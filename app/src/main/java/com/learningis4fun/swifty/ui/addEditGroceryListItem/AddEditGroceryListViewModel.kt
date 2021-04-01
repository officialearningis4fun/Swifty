package com.learningis4fun.swifty.ui.addEditGroceryListItem

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.learningis4fun.swifty.data.Category
import com.learningis4fun.swifty.data.GroceryListItem
import com.learningis4fun.swifty.data.Repository
import com.learningis4fun.swifty.data.local.entities.CollectionEntityCategoryEntityCrossRef
import com.learningis4fun.swifty.data.local.util.UpdateDataForApp
import com.learningis4fun.swifty.util.Util
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

@ExperimentalCoroutinesApi
class AddEditGroceryListViewModel @ViewModelInject constructor(
    private val repository: Repository,
    private val updateDataForApp: UpdateDataForApp,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val groceryListItem = savedStateHandle.get<GroceryListItem>("groceryListItem")
    private val collectionId = savedStateHandle.get<Int>("collectionId") ?: -1
    private val previousCategoryId = groceryListItem?.categoryId ?: -1
    private val _groceryListItem = MutableStateFlow(groceryListItem ?: GroceryListItem())

    val groceryListItemData = _groceryListItem.value
    private val categories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())

    val toolbarTitle = if (groceryListItem == null) "Add Item" else "Edit Item"

    private val addEditGroceryListFragmentEventsChannel =
        Channel<AddEditGroceryListFragmentEvents>()
    val addEditGroceryListFragmentEvents = addEditGroceryListFragmentEventsChannel.receiveAsFlow()

    fun enableSaveBtn() = _groceryListItem.mapLatest {
        val nameAvailable = it.name.isNotBlank()
        val priceAvailable = it.pricePerItem > 0.0
        val categoryAvailable = it.categoryId > 0
        val numberOfItemsAvailable = it.numberOfItems > 0
        return@mapLatest nameAvailable && priceAvailable && categoryAvailable && numberOfItemsAvailable
    }.asLiveData()

    fun categoryText() = _groceryListItem.mapLatest { groceryItem ->
        var newCatId = 0
        val catId = groceryItem.categoryId
        if (categories.value.isEmpty()) {
            categories.value = repository.getAllCategories()
        }

        val catName: String

        catName = if (catId == 0) {
            newCatId = categories.value.first().id
            categories.value.first().name

        } else {
            val cats = categories.value.filter {
                it.id == catId
            }
            if (cats.isNotEmpty()) {
                newCatId = cats.first().id
                cats.first().name
            } else {
                newCatId = 0
                ""
            }
        }
        setCategoryId(newCatId)

        return@mapLatest catName
    }.asLiveData()

    fun numberOfItems() = _groceryListItem.map { it.numberOfItems.toString() }.asLiveData()

    private fun setCategoryId(categoryId: Int) {
        _groceryListItem.value = _groceryListItem.value.copy(categoryId = categoryId)
    }


    fun onDetailsChanged(details: String) {
        _groceryListItem.value = _groceryListItem.value.copy(name = details)
    }

    fun onMassWeightChanged(mass: String) {
        val totalMass = mass.toDoubleOrNull()
        _groceryListItem.value = _groceryListItem.value.copy(weightPerItem = totalMass ?: 0.0)
    }

    fun onMassUnitChanged(unit: String) {
        if (unit.isNotEmpty()) {
            _groceryListItem.value = _groceryListItem.value.copy(weightUnits = unit)
        }
    }

    fun onNumberOfItemsChanged(items: String) {
        val numberOfItems = items.toIntOrNull()
        _groceryListItem.value = _groceryListItem.value.copy(numberOfItems = numberOfItems ?: 1)
    }

    fun String.monetize(): String = if (this.isEmpty()) "0"
    else DecimalFormat("#,###").format(this.replace("[^\\d]".toRegex(), "").toLong())

    fun monetizeDouble(value: String, prefix: String): String {
        if (value == ".") return "$prefix ."
        if(value.substringAfter(".") == "") return "${monetizeInteger(value, prefix)}."
        val parsed = BigDecimal(value.replace(",", ""))
        val formatter = DecimalFormat(
            "$prefix#,###.${getDecimalPattern(value)}",
            DecimalFormatSymbols(Locale.US)
        )
        formatter.roundingMode = RoundingMode.DOWN
        return formatter.format(parsed)
    }

    fun monetizeInteger(value: String, prefix: String): String {
        val parsed = BigDecimal(value.replace(",", ""))
        val formatter = DecimalFormat("$prefix#,###", DecimalFormatSymbols(Locale.US))
        return formatter.format(parsed)
    }

    private fun getDecimalPattern(value: String): String {
        val decimalCount = value.length - value.indexOf(".") - 1
        val decimalPattern = StringBuilder()

        if(decimalCount < 2){
            for(i in 0..decimalCount){
                decimalPattern.append("0")
            }
        } else {
            decimalPattern.append("00")
        }

        return decimalPattern.toString()
    }

    fun decimalCountReachedMax(value: String): Boolean = (value.length - value.indexOf(".") - 1) > 1

    fun onPricePerItemChanged(priceString: String) {
        val price = priceString.toDoubleOrNull()
        _groceryListItem.value = _groceryListItem.value.copy(pricePerItem = price ?: 0.0)
    }

    fun onTotalPriceChanged(totalPriceString: String) {
        val totalPrice = try {
            totalPriceString.toDouble()
        } catch (exception: Exception) {
            0.0
        }
        _groceryListItem.value = _groceryListItem.value.copy(totalPrice = totalPrice)
    }

    fun onIncrementNumberOfItemsClicked() {
        val numOfItems = _groceryListItem.value.numberOfItems + 1
        _groceryListItem.value = _groceryListItem.value.copy(numberOfItems = numOfItems)
    }

    fun onDecrementNumberOfItemsClicked() {
        if (_groceryListItem.value.numberOfItems > 0) {
            val numOfItems = _groceryListItem.value.numberOfItems - 1
            _groceryListItem.value = _groceryListItem.value.copy(numberOfItems = numOfItems)
        }
    }

    fun onCategoryClicked() = viewModelScope.launch {
        val cat = categories.value.firstOrNull { category ->
            category.id == _groceryListItem.value.categoryId
        }

        addEditGroceryListFragmentEventsChannel.send(
            AddEditGroceryListFragmentEvents.ShowCategorySingleChoiceScreen(
                categories.value.map {
                    it.name
                }.toTypedArray(),
                if (cat == null) {
                    0
                } else {
                    categories.value.indexOf(cat)
                }

            )
        )
    }

    fun onCategorySet(itemClicked: Int) = viewModelScope.launch {
        val catId = categories.value[itemClicked].id
        _groceryListItem.value = _groceryListItem.value.copy(categoryId = catId)
    }

    fun ifKgConvertToGram(groceryListItem: GroceryListItem): GroceryListItem {

        val massPerItem: Double = if (groceryListItem.weightUnits == "kg") {
            // convert weight to grams
            groceryListItem.weightPerItem * 1000
        } else {
            groceryListItem.weightPerItem
        }

        return groceryListItem.copy(
            weightPerItem = massPerItem,
            totalWeight = massPerItem * groceryListItem.numberOfItems
        )
    }

    fun onSaveClick() = viewModelScope.launch {

        if (groceryListItem == null) {
            createGroceryListItem()
        } else {
            updateGroceryListItem()
        }

        addEditGroceryListFragmentEventsChannel.send(
            AddEditGroceryListFragmentEvents.NavigateBackToPreviousScreen(
                _groceryListItem.value
            )
        )
    }

    val groceryList = _groceryListItem.value

    private suspend fun createGroceryListItem() {
        // insert collectionId in groceryListItem
        val groceryListItem = _groceryListItem.value.copy(
            collectionId = collectionId, totalPrice = Util.getTotalPrice(
                _groceryListItem.value.pricePerItem, _groceryListItem.value.numberOfItems
            )
        )

        // save collectionId and categoryId in collectionCategoryCrossRef table
        repository.insertCollectionIdAndCategoryId(
            CollectionEntityCategoryEntityCrossRef(
                groceryCollectionEntityId = collectionId,
                groceryListItemCategoryId = groceryListItem.categoryId
            )
        )
        // save groceryListEntity in db
        repository.insertGroceryListItem(ifKgConvertToGram(groceryListItem))
        updateDataForApp.updateCollectionTotalPriceAndTotalWeight(collectionId)
    }

    fun convertMassFromGramsToKg(mass: Double?): String {
        var massText = ""

        mass?.let {
            val massInGrams = it / 1000
            if (massInGrams != 0.0) {
                massText = Util.removeUnnecessaryDecimals(massInGrams)
            }
        }

        return massText
    }

    private suspend fun updateGroceryListItem() {
        // insert collectionId in groceryListItem
        val groceryListItem = _groceryListItem.value.copy(
            collectionId = collectionId, totalPrice = Util.getTotalPrice(
                _groceryListItem.value.pricePerItem, _groceryListItem.value.numberOfItems
            )
        )

        // save collectionId and categoryId in collectionCategoryCrossRef table
        repository.insertCollectionIdAndCategoryId(
            CollectionEntityCategoryEntityCrossRef(
                groceryCollectionEntityId = collectionId,
                groceryListItemCategoryId = groceryListItem.categoryId
            )
        )
        // save groceryListEntity in db
        repository.updateGroceryListItem(ifKgConvertToGram(groceryListItem))
        updateDataForApp.updateCollectionTotalPriceAndTotalWeight(collectionId)

        if (previousCategoryId != -1) {
            updateDataForApp.updateGroceryListItemCategory(
                collectionId,
                previousCategoryId
            )
        }
    }

    sealed class AddEditGroceryListFragmentEvents {
        data class NavigateBackToPreviousScreen(val groceryListItem: GroceryListItem) :
            AddEditGroceryListFragmentEvents()

        data class ShowCategorySingleChoiceScreen(
            val list: Array<String>,
            val currCategoryPosition: Int
        ) :
            AddEditGroceryListFragmentEvents() {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as ShowCategorySingleChoiceScreen

                if (!list.contentEquals(other.list)) return false
                if (currCategoryPosition != other.currCategoryPosition) return false

                return true
            }

            override fun hashCode(): Int {
                var result = list.contentHashCode()
                result = 31 * result + currCategoryPosition
                return result
            }
        }
    }
}
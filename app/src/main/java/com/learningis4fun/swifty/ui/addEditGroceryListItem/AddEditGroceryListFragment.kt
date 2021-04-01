package com.learningis4fun.swifty.ui.addEditGroceryListItem

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.learningis4fun.swifty.R
import com.learningis4fun.swifty.data.GroceryListItem
import com.learningis4fun.swifty.databinding.FragmentAddEditGroceryListBinding
import com.learningis4fun.swifty.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.lang.ref.WeakReference
import java.text.DecimalFormat

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AddEditGroceryListFragment : Fragment(R.layout.fragment_add_edit_grocery_list),
    CategorySingleChoiceDialog.CategorySingleChoiceDialogListener {

    private var binding: FragmentAddEditGroceryListBinding? = null
    val viewModel: AddEditGroceryListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddEditGroceryListBinding.bind(view)

        binding?.apply {
            addEditToolbar.title = viewModel.toolbarTitle
            addEditToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            itemDetails.addTextChangedListener {
                viewModel.onDetailsChanged(it.toString())
            }
            decrementNumberBtn.setOnClickListener {
                viewModel.onDecrementNumberOfItemsClicked()
            }
            incrementNumberBtn.setOnClickListener {
                viewModel.onIncrementNumberOfItemsClicked()
            }
            saveBtn.setOnClickListener {
                viewModel.onSaveClick()
            }
            category.setOnClickListener {
                viewModel.onCategoryClicked()
            }

            itemPrice.setMaskingMoney("M ")

            inputMassWeight.addTextChangedListener {
                viewModel.onMassWeightChanged(it.toString())
            }

            inputNumberOfItems.addTextChangedListener {
                viewModel.onNumberOfItemsChanged(it.toString())
            }

            radioGroupMassUnit.setOnCheckedChangeListener { radioGroup, _ ->
                val unit: String = when (radioGroup.checkedRadioButtonId) {
                    R.id.radio_btn_mass_kg -> {
                        resources.getString(R.string.text_item_radio_btn_kilogram_fragment_add_edit_grocery_list)
                    }
                    R.id.radio_btn_mass_g -> {
                        resources.getString(R.string.text_item_radio_btn_gram_fragment_add_edit_grocery_list)
                    }
                    else -> ""
                }
                viewModel.onMassUnitChanged(unit)
            }
        }
        setData(viewModel.groceryListItemData)
        registerObservers()
        registerEventsListener()
    }

    fun EditText.setMaskingMoney(currencyText: String) {
        val editTextWeakReference: WeakReference<EditText> =
            WeakReference(this@setMaskingMoney)

        this.addTextChangedListener(object : MyTextWatcher {
            val prefix = "M "
            var previousCleanString = ""
            var decimalCount = 0

            override fun afterTextChanged(editable: Editable?) {

                val editText = editTextWeakReference.get() ?: return

                val s = editable.toString()
                if (s.length < prefix.length) {
                    viewModel.onPricePerItemChanged("")
                    editText.setText(prefix)
                    editText.setSelection(prefix.length)
                    return
                }

                if (s == prefix) {
                    viewModel.onPricePerItemChanged("")
                    return
                }

                val replaceable = String.format("[%s,.\\s]", "M")
                val cleanString = s.replace(prefix, "")
                viewModel.onPricePerItemChanged(cleanString)

                if (cleanString == previousCleanString || cleanString.isEmpty()) {
                    return
                }

                //Toast.makeText(requireContext(), cleanString, Toast.LENGTH_SHORT).show()

                previousCleanString = cleanString

                val formatted = if (cleanString.contains(".")) {

                    viewModel.monetizeDouble(cleanString, prefix)

                    /*if (cleanString.substringAfter(".") == "")
                        viewModel.monetizeDouble(cleanString, prefix)
                    else
                        viewModel.monetizeInteger(cleanString.substringBefore("."), prefix)*/

                } else {
                    viewModel.monetizeInteger(cleanString, prefix)
                }

                editText.removeTextChangedListener(this)
                editText.setText(formatted)
                editText.setSelection(formatted.length)
                editText.addTextChangedListener(this)

            }
        })
    }

    interface MyTextWatcher : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    private fun setData(groceryListItem: GroceryListItem?) {
        binding?.apply {
            val kgMassUnits =
                resources.getString(R.string.text_item_radio_btn_kilogram_fragment_add_edit_grocery_list)
            val currMassUnits = groceryListItem?.weightUnits ?: ""

            itemDetails.setText(groceryListItem?.name ?: "")
            itemPrice.setText(
                if (groceryListItem?.pricePerItem != 0.0) groceryListItem?.pricePerItem.toString()
                else ""
            )
            inputNumberOfItems.setText(groceryListItem?.numberOfItems.toString())

            inputMassWeight.setText(
                if (kgMassUnits == currMassUnits) {
                    // mass is in kg
                    viewModel.convertMassFromGramsToKg(groceryListItem?.weightPerItem)
                } else {
                    Util.removeUnnecessaryDecimals(groceryListItem?.weightPerItem ?: 0.0)
                }

            )

            radioGroupMassUnit.check(
                if (currMassUnits == kgMassUnits) R.id.radio_btn_mass_kg
                else {
                    viewModel.onMassUnitChanged(resources.getString(R.string.text_item_radio_btn_gram_fragment_add_edit_grocery_list))
                    R.id.radio_btn_mass_g
                }
            )
        }
    }

    private fun registerObservers() {
        viewModel.enableSaveBtn().observe(viewLifecycleOwner) {
            binding?.saveBtn?.isEnabled = it
        }

        viewModel.categoryText().observe(viewLifecycleOwner) {
            binding?.category?.text = it
        }

        viewModel.numberOfItems().observe(viewLifecycleOwner) {
            binding?.inputNumberOfItems?.setText(it)
        }
    }

    private fun registerEventsListener() {
        lifecycleScope.launchWhenStarted {
            viewModel.addEditGroceryListFragmentEvents.collect { event ->
                when (event) {
                    is AddEditGroceryListViewModel.AddEditGroceryListFragmentEvents.NavigateBackToPreviousScreen -> {
                        findNavController().popBackStack()
                    }
                    is AddEditGroceryListViewModel.AddEditGroceryListFragmentEvents.ShowCategorySingleChoiceScreen -> {
                        openCategorySingleChoiceDialog(event.list, event.currCategoryPosition)
                    }
                }
            }
        }
    }

    private fun openCategorySingleChoiceDialog(list: Array<String>, checkedItem: Int) {
        val dialog = CategorySingleChoiceDialog(this, list, checkedItem)
        dialog.show(childFragmentManager, "category chooser")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Util.hideKeyBoard(requireActivity(), binding?.itemDetails)
        binding = null
    }

    override fun onItemClicked(itemPosition: Int) {
        viewModel.onCategorySet(itemPosition)
    }
}
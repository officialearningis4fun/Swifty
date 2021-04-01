package com.learningis4fun.swifty.ui.addEditGroceryListItem

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CategorySingleChoiceDialog(
    private val listener: CategorySingleChoiceDialogListener,
    private val list: Array<String>?,
    private val checkedItem: Int
) :
    DialogFragment() {

    interface CategorySingleChoiceDialogListener {
        fun onItemClicked(itemPosition: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Category")
        builder.setSingleChoiceItems(list, checkedItem) { _, clickedItemPos ->
            listener.onItemClicked(clickedItemPos)
            dismiss()
        }
        return builder.create()
    }
}
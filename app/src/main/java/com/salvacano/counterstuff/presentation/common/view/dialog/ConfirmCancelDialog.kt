package com.salvacano.counterstuff.presentation.common.view.dialog

import com.salvacano.counterstuff.R
import com.salvacano.counterstuff.databinding.DialogConfirmCancelBinding

class ConfirmCancelDialog(
    private val title: String,
    private val message: String,
    private val onConfirm: () -> Unit
): LastTimeBaseBottomSheetDialogFragment<DialogConfirmCancelBinding>() {

    override val layoutId = R.layout.dialog_confirm_cancel

    override fun DialogConfirmCancelBinding.initialize() {
        this.titleString = title
        this.messageString = message
        confirm.setOnClickListener {
            onConfirm()
            dismiss()
        }
        cancel.setOnClickListener { dismiss() }
    }

}
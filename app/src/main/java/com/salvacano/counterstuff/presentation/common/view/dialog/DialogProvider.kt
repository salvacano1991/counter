package com.salvacano.counterstuff.presentation.common.view.dialog

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.salvacano.counterstuff.R

class DialogProvider {

    fun getDownloadNewVersionDialog(
        context: Context,
        fragmentManager: FragmentManager,
        onAccept: () -> Unit
    ) {
        ConfirmCancelDialog(
            context.getString(R.string.download_new_version),
            context.getString(R.string.download_new_version_message),
            onAccept
        ).apply {
            show(fragmentManager, "DownloadNewVersionDialog")
        }
    }

}
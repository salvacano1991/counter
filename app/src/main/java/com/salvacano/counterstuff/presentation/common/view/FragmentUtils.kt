package com.salvacano.counterstuff.presentation.common.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.salvacano.counterstuff.presentation.common.view.BaseActivity.Companion.DEFAULT_REQUEST_CODE
import com.salvacano.counterstuff.presentation.common.view.BaseFragment.Companion.EXTRA_DATA
import java.io.Serializable

fun <T : Serializable> Class<out Fragment>.newInstance(data: T?): Fragment {
    return this.newInstance().apply {
        val bundle = Bundle()
        bundle.putSerializable(EXTRA_DATA, data)
        arguments = bundle
    }
}

fun <T : Serializable> Fragment.navigate(clazz: Class<out AppCompatActivity>, requestCode: Int = DEFAULT_REQUEST_CODE, extraData: T? = null, flags: Int? = null) {
    val intent = Intent(context, clazz)
    if (extraData != null) intent.putExtra(EXTRA_DATA, extraData)
    if (flags != null) intent.flags = flags
    startActivityForResult(intent, requestCode)
}

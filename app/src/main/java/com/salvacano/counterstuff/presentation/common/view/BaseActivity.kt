package com.salvacano.counterstuff.presentation.common.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.salvacano.counterstuff.R
import java.io.Serializable

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        const val DEFAULT_REQUEST_CODE = 1
        const val DEFAULT_CONTAINER_ID = android.R.id.content
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        initialize()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) finish()
        else super.onBackPressed()
    }

    abstract fun initialize()

    fun <T : Serializable> replaceFragment(fragment: Class<out Fragment>, containerId: Int = DEFAULT_CONTAINER_ID, extraData: T? = null) {
        val newFragment = fragment.newInstance(extraData)
        supportFragmentManager.beginTransaction().replace(containerId, newFragment).commit()
    }

}

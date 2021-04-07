package com.salvacano.counterstuff.presentation.login.view

import android.os.Bundle
import com.salvacano.counterstuff.R
import com.salvacano.counterstuff.presentation.common.view.BaseActivity

class LoginActivity : BaseActivity() {

    override fun initialize() {
        replaceFragment(LoginFragment::class.java, extraData = null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AppCompat_NoActionBar)
        super.onCreate(savedInstanceState)
    }

}

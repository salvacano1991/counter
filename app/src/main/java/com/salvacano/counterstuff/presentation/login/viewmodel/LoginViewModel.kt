package com.salvacano.counterstuff.presentation.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.salvacano.counterstuff.BuildConfig
import com.salvacano.counterstuff.presentation.common.viewmodel.CounterStuffBaseViewModel

class LoginViewModel : CounterStuffBaseViewModel() {

    companion object {
        const val VERSION_CODE_NAME = "current_prod_version"
    }

    private var _requestGoogleLogin = MutableLiveData<Boolean>()
    val requestGoogleLogin: LiveData<Boolean>
        get() = _requestGoogleLogin

    private var _requestLoginSkip = MutableLiveData<Boolean>()
    val requestLoginSkip: LiveData<Boolean>
        get() = _requestLoginSkip

    private var _isOldVersion = MutableLiveData<Boolean>()
    val isOldVersion: LiveData<Boolean>
        get() = _isOldVersion

    fun googleLoginClick() {
        _requestGoogleLogin.postValue(true)
    }

    fun skipLoginClick() {
        _requestLoginSkip.postValue(true)
    }

    fun checkVersion() {
        runOnBackground {
            val instance = FirebaseRemoteConfig.getInstance()
            val settings = FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(3600).build()
            instance.setConfigSettingsAsync(settings)
            instance.setDefaultsAsync(mapOf(VERSION_CODE_NAME to BuildConfig.VERSION_CODE))
            instance.fetchAndActivate().addOnCompleteListener {
                if (it.isSuccessful) {
                    val value = FirebaseRemoteConfig.getInstance().getString(VERSION_CODE_NAME)
                    _isOldVersion.postValue(BuildConfig.VERSION_CODE < value.toInt())
                } else {
                    _isOldVersion.postValue(false)
                }
            }
        }
    }

}
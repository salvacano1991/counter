package com.salvacano.counterstuff

import android.app.Application
import com.google.firebase.FirebaseApp

internal class CounterStuffApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

}
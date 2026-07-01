package com.example.ecommerceshop

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.initialize

class EcommerceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Firebase is automatically initialized by the google-services plugin via ContentProvider.
        // Firebase.initialize(this)
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(DebugAppCheckProviderFactory.getInstance())
    }
}
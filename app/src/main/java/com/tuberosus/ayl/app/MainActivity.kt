package com.tuberosus.ayl.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tuberosus.ayl.BuildConfig
import com.tuberosus.ayl.ui.theme.AYLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        if (BuildConfig.DEBUG) {
            Firebase.firestore.useEmulator("10.0.2.2", 8080)
        }

        setContent {
            AYLTheme {
                AppRoot()
            }
        }
    }
}
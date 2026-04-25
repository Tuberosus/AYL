package com.tuberosus.ayl.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tuberosus.ayl.feature.contacts.ContactsScreenRoot
import com.tuberosus.ayl.ui.theme.AYLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AYLTheme {
                ContactsScreenRoot()
            }
        }
    }
}
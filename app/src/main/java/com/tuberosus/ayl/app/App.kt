package com.tuberosus.ayl.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.tuberosus.ayl.navigation.AppBottomBar
import com.tuberosus.ayl.navigation.AppNavGraph

@Composable
fun App() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            AppBottomBar(navController)
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
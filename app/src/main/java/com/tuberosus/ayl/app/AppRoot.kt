package com.tuberosus.ayl.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.tuberosus.ayl.navigation.AppBottomBar
import com.tuberosus.ayl.navigation.AppNavGraph
import com.tuberosus.ayl.ui.components.BackTopAppBar

@Composable
fun AppRoot() {
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
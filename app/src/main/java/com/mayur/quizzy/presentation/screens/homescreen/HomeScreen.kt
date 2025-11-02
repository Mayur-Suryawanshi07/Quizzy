package com.mayur.quizzy.presentation.screens.homescreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mayur.quizzy.presentation.screens.components.MyBottomNav
import com.mayur.quizzy.presentation.screens.components.MyTopAppBar

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {
            MyTopAppBar("Quizz App", {},{})
        },
        bottomBar = {
            MyBottomNav()
        },


    ) {

    }

}
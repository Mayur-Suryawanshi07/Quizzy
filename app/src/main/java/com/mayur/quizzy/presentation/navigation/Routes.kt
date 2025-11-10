package com.mayur.quizzy.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes{
    @Serializable
    data object Login : Routes()

    @Serializable
    data object SignUp : Routes()

    @Serializable
    data object Home : Routes()

    @Serializable
    data object Updates : Routes()

    @Serializable
    data object Profile : Routes()

}

sealed class Graph{

    @Serializable
    data object Auth : Graph()

    @Serializable
    data object Main : Graph()
}
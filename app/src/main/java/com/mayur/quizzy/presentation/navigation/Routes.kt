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

    @Serializable
    data object Technology : Routes()

    @Serializable
    data object JavaQuiz : Routes()

    @Serializable
    data object DBMSQuiz : Routes()

    @Serializable
    data object OSQuiz : Routes()

    @Serializable
    data object CNQuiz : Routes()

    @Serializable
    data object CreateQuiz : Routes()

    @Serializable
    data object CreatedQuizzes : Routes()

    @Serializable
    data object ChatBot : Routes()

    @Serializable
    data object EditProfile : Routes()

}

sealed class Graph{

    @Serializable
    data object Auth : Graph()

    @Serializable
    data object Main : Graph()
}
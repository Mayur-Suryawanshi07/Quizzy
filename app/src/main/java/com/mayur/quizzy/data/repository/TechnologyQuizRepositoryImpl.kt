package com.mayur.quizzy.data.repository

import com.mayur.quizzy.data.local.datasource.CnQuestions
import com.mayur.quizzy.data.local.datasource.DbmsQuestions
import com.mayur.quizzy.data.local.datasource.JavaQuestions
import com.mayur.quizzy.data.local.datasource.OsQuestions
import com.mayur.quizzy.domain.model.technology.TechnologyQuiz
import com.mayur.quizzy.domain.model.quiz.questions.Question
import com.mayur.quizzy.domain.repository.TechnologyQuizRepository
import javax.inject.Inject

class TechnologyQuizRepositoryImpl @Inject constructor() : TechnologyQuizRepository {

    override fun getTechnologyQuizzes(): List<TechnologyQuiz> {
        return listOf(
            TechnologyQuiz(
                id = "java",
                title = "Java",
                description = "Test your Java programming knowledge",
                iconName = "computer",
                colorHex = 0xFF2196F3
            ),
            TechnologyQuiz(
                id = "dbms",
                title = "DBMS",
                description = "Database Management System quiz",
                iconName = "storage",
                colorHex = 0xFF9C27B0
            ),
            TechnologyQuiz(
                id = "os",
                title = "OS",
                description = "Operating System fundamentals",
                iconName = "settings",
                colorHex = 0xFF00BCD4
            ),
            TechnologyQuiz(
                id = "cn",
                title = "CN",
                description = "Computer Networks quiz",
                iconName = "wifi",
                colorHex = 0xFF8BC34A
            )
        )
    }

    override fun getQuestions(quizId: String): List<Question> {
        return when (quizId) {
            "java" -> JavaQuestions.all()
            "dbms" -> DbmsQuestions.all()
            "os" -> OsQuestions.all()
            "cn" -> CnQuestions.all()
            else -> emptyList()
        }
    }
}

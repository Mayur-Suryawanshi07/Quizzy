package com.mayur.quizzy.data.local.datasource

import com.mayur.quizzy.domain.model.quiz.questions.Question

object DbmsQuestions {
    fun all(): List<Question> {

    return listOf(
        Question(
            id = 1,
            question = "What does DBMS stand for?",
            options = listOf("Database Management System", "Data Backup Management System", "Database Modeling System", "Data Business Management System"),
            correctAnswerIndex = 0,
            explanation = "DBMS stands for Database Management System."
        ),
        Question(
            id = 2,
            question = "Which SQL command is used to retrieve data from a database?",
            options = listOf("GET", "SELECT", "FETCH", "RETRIEVE"),
            correctAnswerIndex = 1,
            explanation = "SELECT is used to retrieve data from a database."
        ),
        Question(
            id = 3,
            question = "What is a primary key?",
            options = listOf("A key that can be null", "A unique identifier for a record", "A foreign key", "A composite key"),
            correctAnswerIndex = 1,
            explanation = "A primary key is a unique identifier for a record in a table."
        ),
        Question(
            id = 4,
            question = "Which normalization form eliminates partial dependencies?",
            options = listOf("1NF", "2NF", "3NF", "BCNF"),
            correctAnswerIndex = 1,
            explanation = "2NF eliminates partial dependencies."
        ),
        Question(
            id = 5,
            question = "What is ACID in database transactions?",
            options = listOf("Atomicity, Consistency, Isolation, Durability", "Access, Control, Integrity, Data", "Analysis, Creation, Insertion, Deletion", "Application, Connection, Interface, Database"),
            correctAnswerIndex = 0,
            explanation = "ACID stands for Atomicity, Consistency, Isolation, and Durability."
        ),
        Question(
            id = 6,
            question = "Which SQL clause is used to filter records?",
            options = listOf("FILTER", "WHERE", "HAVING", "CONDITION"),
            correctAnswerIndex = 1,
            explanation = "WHERE clause is used to filter records."
        ),
        Question(
            id = 7,
            question = "What is a foreign key?",
            options = listOf("A key that is always unique", "A key that references a primary key in another table", "A key that cannot be null", "A composite key"),
            correctAnswerIndex = 1,
            explanation = "A foreign key references a primary key in another table."
        ),
        Question(
            id = 8,
            question = "Which join returns all records from the left table?",
            options = listOf("INNER JOIN", "LEFT JOIN", "RIGHT JOIN", "FULL JOIN"),
            correctAnswerIndex = 1,
            explanation = "LEFT JOIN returns all records from the left table."
        ),
        Question(
            id = 9,
            question = "What does SQL stand for?",
            options = listOf("Structured Query Language", "Simple Query Language", "Standard Query Language", "System Query Language"),
            correctAnswerIndex = 0,
            explanation = "SQL stands for Structured Query Language."
        ),
        Question(
            id = 10,
            question = "Which command is used to delete all records from a table?",
            options = listOf("DELETE", "DROP", "TRUNCATE", "REMOVE"),
            correctAnswerIndex = 2,
            explanation = "TRUNCATE is used to delete all records from a table."
        ),
        Question(
            id = 11,
            question = "What is an index in a database?",
            options = listOf("A table", "A data structure that improves query performance", "A constraint", "A view"),
            correctAnswerIndex = 1,
            explanation = "An index is a data structure that improves query performance."
        ),
        Question(
            id = 12,
            question = "Which SQL function returns the number of rows?",
            options = listOf("SUM()", "COUNT()", "TOTAL()", "NUMBER()"),
            correctAnswerIndex = 1,
            explanation = "COUNT() returns the number of rows."
        ),
        Question(
            id = 13,
            question = "What is a view in SQL?",
            options = listOf("A physical table", "A virtual table based on a query", "A stored procedure", "A trigger"),
            correctAnswerIndex = 1,
            explanation = "A view is a virtual table based on a SQL query."
        ),
        Question(
            id = 14,
            question = "Which constraint ensures all values in a column are unique?",
            options = listOf("PRIMARY KEY", "UNIQUE", "NOT NULL", "CHECK"),
            correctAnswerIndex = 1,
            explanation = "UNIQUE constraint ensures all values in a column are unique."
        ),
        Question(
            id = 15,
            question = "What is the purpose of GROUP BY clause?",
            options = listOf("To filter rows", "To group rows with same values", "To sort rows", "To join tables"),
            correctAnswerIndex = 1,
            explanation = "GROUP BY groups rows with the same values."
        )
    )
    }
}

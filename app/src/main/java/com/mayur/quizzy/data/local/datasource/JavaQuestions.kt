package com.mayur.quizzy.data.local.datasource

import com.mayur.quizzy.domain.model.quiz.questions.Question

object JavaQuestions {
    fun all(): List<Question> {

    return listOf(
        Question(
            id = 1,
            question = "What is the default value of a boolean variable in Java?",
            options = listOf("true", "false", "null", "0"),
            correctAnswerIndex = 1,
            explanation = "In Java, boolean variables default to false."
        ),
        Question(
            id = 2,
            question = "Which keyword is used to inherit a class in Java?",
            options = listOf("extends", "implements", "inherits", "super"),
            correctAnswerIndex = 0,
            explanation = "The 'extends' keyword is used for class inheritance in Java."
        ),
        Question(
            id = 3,
            question = "What is the size of an int in Java?",
            options = listOf("16 bits", "32 bits", "64 bits", "8 bits"),
            correctAnswerIndex = 1,
            explanation = "An int in Java is 32 bits (4 bytes)."
        ),
        Question(
            id = 4,
            question = "Which method is used to start a thread in Java?",
            options = listOf("run()", "start()", "execute()", "begin()"),
            correctAnswerIndex = 1,
            explanation = "The start() method is used to start a thread, which then calls run()."
        ),
        Question(
            id = 5,
            question = "What is the parent class of all classes in Java?",
            options = listOf("Object", "Class", "Super", "Parent"),
            correctAnswerIndex = 0,
            explanation = "Object is the root class of all classes in Java."
        ),
        Question(
            id = 6,
            question = "Which collection class is synchronized in Java?",
            options = listOf("ArrayList", "HashMap", "Vector", "HashSet"),
            correctAnswerIndex = 2,
            explanation = "Vector is synchronized, making it thread-safe but slower."
        ),
        Question(
            id = 7,
            question = "What is the output of: System.out.println(5 + 5 + \"5\");",
            options = listOf("55", "105", "555", "15"),
            correctAnswerIndex = 1,
            explanation = "First 5+5=10, then 10+\"5\"=\"105\" (string concatenation)."
        ),
        Question(
            id = 8,
            question = "Which access modifier provides the widest access?",
            options = listOf("private", "protected", "default", "public"),
            correctAnswerIndex = 3,
            explanation = "public provides the widest access - accessible from anywhere."
        ),
        Question(
            id = 9,
            question = "What is a constructor in Java?",
            options = listOf(
                "A method that returns a value",
                "A special method to initialize objects",
                "A variable",
                "A class"
            ),
            correctAnswerIndex = 1,
            explanation = "A constructor is a special method used to initialize objects."
        ),
        Question(
            id = 10,
            question = "Which keyword is used to prevent method overriding?",
            options = listOf("static", "final", "private", "abstract"),
            correctAnswerIndex = 1,
            explanation = "The 'final' keyword prevents method overriding."
        ),
        Question(
            id = 11,
            question = "What is the purpose of the 'this' keyword in Java?",
            options = listOf(
                "To call a superclass method",
                "To refer to the current object",
                "To create a new object",
                "To delete an object"
            ),
            correctAnswerIndex = 1,
            explanation = "'this' refers to the current instance of the class."
        ),
        Question(
            id = 12,
            question = "Which interface must be implemented for serialization?",
            options = listOf("Serializable", "Cloneable", "Comparable", "Runnable"),
            correctAnswerIndex = 0,
            explanation = "Serializable interface must be implemented for object serialization."
        ),
        Question(
            id = 13,
            question = "What is the difference between == and .equals() in Java?",
            options = listOf(
                "== compares references, .equals() compares values",
                "== compares values, .equals() compares references",
                "They are the same",
                "== is for primitives, .equals() is for objects"
            ),
            correctAnswerIndex = 0,
            explanation = "== compares object references, .equals() compares actual values."
        ),
        Question(
            id = 14,
            question = "What is method overloading?",
            options = listOf(
                "Having multiple methods with same name but different parameters",
                "Having multiple methods with same name and same parameters",
                "Inheriting methods from parent class",
                "Overriding parent class methods"
            ),
            correctAnswerIndex = 0,
            explanation = "Method overloading means having multiple methods with the same name but different parameters."
        ),
        Question(
            id = 15,
            question = "Which data structure follows LIFO principle?",
            options = listOf("Queue", "Stack", "List", "Set"),
            correctAnswerIndex = 1,
            explanation = "Stack follows Last In First Out (LIFO) principle."
        ),
        Question(
            id = 16,
            question = "What is the default value of a String variable in Java?",
            options = listOf("empty string", "null", "\"\"", "undefined"),
            correctAnswerIndex = 1,
            explanation = "String variables default to null in Java."
        ),
        Question(
            id = 17,
            question = "Which exception is thrown when dividing by zero?",
            options = listOf("NullPointerException", "ArithmeticException", "NumberFormatException", "IndexOutOfBoundsException"),
            correctAnswerIndex = 1,
            explanation = "ArithmeticException is thrown when dividing by zero."
        ),
        Question(
            id = 18,
            question = "What is the purpose of the 'static' keyword?",
            options = listOf(
                "To make a variable constant",
                "To make a method/variable belong to the class rather than instance",
                "To prevent inheritance",
                "To make a class final"
            ),
            correctAnswerIndex = 1,
            explanation = "static makes members belong to the class rather than instances."
        ),
        Question(
            id = 19,
            question = "Which loop is guaranteed to execute at least once?",
            options = listOf("for loop", "while loop", "do-while loop", "enhanced for loop"),
            correctAnswerIndex = 2,
            explanation = "do-while loop executes at least once before checking the condition."
        ),
        Question(
            id = 20,
            question = "What is the output of: int x = 5; System.out.println(x++);",
            options = listOf("5", "6", "4", "Error"),
            correctAnswerIndex = 0,
            explanation = "x++ is post-increment, so it prints 5 first, then increments to 6."
        )
    )
    }
}

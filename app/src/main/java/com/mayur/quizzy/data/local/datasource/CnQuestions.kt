package com.mayur.quizzy.data.local.datasource

import com.mayur.quizzy.domain.model.quiz.questions.Question

object CnQuestions {
    fun all(): List<Question> {

    return listOf(
        Question(
            id = 1,
            question = "What does CN stand for?",
            options = listOf("Computer Network", "Computer Node", "Central Network", "Communication Network"),
            correctAnswerIndex = 0,
            explanation = "CN stands for Computer Network."
        ),
        Question(
            id = 2,
            question = "Which layer of OSI model handles routing?",
            options = listOf("Physical", "Data Link", "Network", "Transport"),
            correctAnswerIndex = 2,
            explanation = "Network layer handles routing."
        ),
        Question(
            id = 3,
            question = "What is the full form of TCP?",
            options = listOf("Transmission Control Protocol", "Transfer Control Protocol", "Transmission Communication Protocol", "Transfer Communication Protocol"),
            correctAnswerIndex = 0,
            explanation = "TCP stands for Transmission Control Protocol."
        ),
        Question(
            id = 4,
            question = "Which protocol is used for email?",
            options = listOf("HTTP", "FTP", "SMTP", "TCP"),
            correctAnswerIndex = 2,
            explanation = "SMTP (Simple Mail Transfer Protocol) is used for email."
        ),
        Question(
            id = 5,
            question = "What is the default port for HTTP?",
            options = listOf("80", "443", "21", "25"),
            correctAnswerIndex = 0,
            explanation = "HTTP uses port 80 by default."
        ),
        Question(
            id = 6,
            question = "Which topology connects all devices to a central hub?",
            options = listOf("Bus", "Star", "Ring", "Mesh"),
            correctAnswerIndex = 1,
            explanation = "Star topology connects all devices to a central hub."
        ),
        Question(
            id = 7,
            question = "What is the purpose of DNS?",
            options = listOf("To transfer files", "To resolve domain names to IP addresses", "To send emails", "To browse web"),
            correctAnswerIndex = 1,
            explanation = "DNS resolves domain names to IP addresses."
        ),
        Question(
            id = 8,
            question = "Which is a connectionless protocol?",
            options = listOf("TCP", "UDP", "HTTP", "FTP"),
            correctAnswerIndex = 1,
            explanation = "UDP is a connectionless protocol."
        ),
        Question(
            id = 9,
            question = "What is the full form of IP?",
            options = listOf("Internet Protocol", "Internal Protocol", "Interconnection Protocol", "Interface Protocol"),
            correctAnswerIndex = 0,
            explanation = "IP stands for Internet Protocol."
        ),
        Question(
            id = 10,
            question = "Which device operates at the network layer?",
            options = listOf("Hub", "Switch", "Router", "Repeater"),
            correctAnswerIndex = 2,
            explanation = "Router operates at the network layer."
        ),
        Question(
            id = 11,
            question = "What is the maximum speed of Fast Ethernet?",
            options = listOf("10 Mbps", "100 Mbps", "1000 Mbps", "10000 Mbps"),
            correctAnswerIndex = 1,
            explanation = "Fast Ethernet has a maximum speed of 100 Mbps."
        ),
        Question(
            id = 12,
            question = "Which protocol is used for secure web browsing?",
            options = listOf("HTTP", "HTTPS", "FTP", "SMTP"),
            correctAnswerIndex = 1,
            explanation = "HTTPS is used for secure web browsing."
        ),
        Question(
            id = 13,
            question = "What is a MAC address?",
            options = listOf("Media Access Control address", "Memory Access Control address", "Main Access Control address", "Multiple Access Control address"),
            correctAnswerIndex = 0,
            explanation = "MAC stands for Media Access Control address."
        ),
        Question(
            id = 14,
            question = "Which layer of OSI model is responsible for error detection?",
            options = listOf("Physical", "Data Link", "Network", "Transport"),
            correctAnswerIndex = 1,
            explanation = "Data Link layer is responsible for error detection."
        ),
        Question(
            id = 15,
            question = "What is the purpose of a firewall?",
            options = listOf("To speed up network", "To protect network from unauthorized access", "To connect networks", "To store data"),
            correctAnswerIndex = 1,
            explanation = "Firewall protects network from unauthorized access."
        )
    )
    }
}

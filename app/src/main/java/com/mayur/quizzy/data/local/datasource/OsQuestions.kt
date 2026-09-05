package com.mayur.quizzy.data.local.datasource

import com.mayur.quizzy.domain.model.quiz.questions.Question

object OsQuestions {
    fun all(): List<Question> {

    return listOf(
        Question(
            id = 1,
            question = "What does OS stand for?",
            options = listOf("Operating System", "Output System", "Order System", "Object System"),
            correctAnswerIndex = 0,
            explanation = "OS stands for Operating System."
        ),
        Question(
            id = 2,
            question = "Which scheduling algorithm provides the shortest average waiting time?",
            options = listOf("FCFS", "SJF", "Round Robin", "Priority"),
            correctAnswerIndex = 1,
            explanation = "Shortest Job First (SJF) provides the shortest average waiting time."
        ),
        Question(
            id = 3,
            question = "What is the main purpose of an operating system?",
            options = listOf("To manage hardware resources", "To compile programs", "To design databases", "To create websites"),
            correctAnswerIndex = 0,
            explanation = "The main purpose of an OS is to manage hardware resources."
        ),
        Question(
            id = 4,
            question = "What is a process?",
            options = listOf("A program in execution", "A file", "A folder", "A device"),
            correctAnswerIndex = 0,
            explanation = "A process is a program in execution."
        ),
        Question(
            id = 5,
            question = "Which memory management technique allows programs larger than physical memory?",
            options = listOf("Paging", "Segmentation", "Virtual Memory", "Cache"),
            correctAnswerIndex = 2,
            explanation = "Virtual Memory allows programs larger than physical memory."
        ),
        Question(
            id = 6,
            question = "What is deadlock?",
            options = listOf("A process that has finished", "A situation where processes are waiting for each other", "A memory error", "A file corruption"),
            correctAnswerIndex = 1,
            explanation = "Deadlock is when processes are waiting for each other indefinitely."
        ),
        Question(
            id = 7,
            question = "Which is a multi-user operating system?",
            options = listOf("DOS", "Windows 95", "Linux", "MS-DOS"),
            correctAnswerIndex = 2,
            explanation = "Linux is a multi-user operating system."
        ),
        Question(
            id = 8,
            question = "What is the purpose of a semaphore?",
            options = listOf("To store data", "To synchronize processes", "To manage files", "To compile code"),
            correctAnswerIndex = 1,
            explanation = "Semaphores are used to synchronize processes."
        ),
        Question(
            id = 9,
            question = "Which file system is used by Windows?",
            options = listOf("ext4", "NTFS", "HFS+", "FAT32"),
            correctAnswerIndex = 1,
            explanation = "NTFS is the primary file system used by Windows."
        ),
        Question(
            id = 10,
            question = "What is context switching?",
            options = listOf("Switching between files", "Switching CPU from one process to another", "Switching memory", "Switching devices"),
            correctAnswerIndex = 1,
            explanation = "Context switching is switching CPU from one process to another."
        ),
        Question(
            id = 11,
            question = "Which memory allocation method suffers from external fragmentation?",
            options = listOf("Paging", "Segmentation", "Both", "Neither"),
            correctAnswerIndex = 1,
            explanation = "Segmentation suffers from external fragmentation."
        ),
        Question(
            id = 12,
            question = "What is a thread?",
            options = listOf("A process", "A lightweight process", "A file", "A device"),
            correctAnswerIndex = 1,
            explanation = "A thread is a lightweight process within a process."
        ),
        Question(
            id = 13,
            question = "Which scheduling algorithm is preemptive?",
            options = listOf("FCFS", "SJF", "Round Robin", "All of the above"),
            correctAnswerIndex = 2,
            explanation = "Round Robin is a preemptive scheduling algorithm."
        ),
        Question(
            id = 14,
            question = "What is the purpose of an interrupt?",
            options = listOf("To stop the system", "To signal the CPU about an event", "To delete files", "To format disk"),
            correctAnswerIndex = 1,
            explanation = "Interrupts signal the CPU about events that need attention."
        ),
        Question(
            id = 15,
            question = "Which is a real-time operating system?",
            options = listOf("Windows 10", "macOS", "VxWorks", "Ubuntu"),
            correctAnswerIndex = 2,
            explanation = "VxWorks is a real-time operating system."
        )
    )
    }
}

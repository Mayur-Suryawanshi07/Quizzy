package com.mayur.quizzy.presentation.screens.technology.cnquiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayur.quizzy.domain.model.quiz.questions.Question
import com.mayur.quizzy.domain.model.quiz.QuizResult
import com.mayur.quizzy.domain.model.quiz.QuizSettings
import com.mayur.quizzy.domain.use_cases.CompleteQuizUseCase
import com.mayur.quizzy.domain.use_cases.GetQuizQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CNQuizViewModel @Inject constructor(
    private val getQuizQuestions: GetQuizQuestionsUseCase,
    private val completeQuiz: CompleteQuizUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CNQuizUiState())
    val uiState: StateFlow<CNQuizUiState> = _uiState.asStateFlow()

    private var selectedQuestions: List<Question> = emptyList()
    private var userAnswers: MutableMap<Int, Int> = mutableMapOf()
    private var startTime: Long = 0
    private var timerJob: Job? = null

    fun showSettingsDialog() {
        _uiState.value = _uiState.value.copy(showSettingsDialog = true)
    }

    fun hideSettingsDialog() {
        _uiState.value = _uiState.value.copy(showSettingsDialog = false)
    }

    fun updateNumberOfQuestions(count: Int) {
        _uiState.value = _uiState.value.copy(selectedQuestionCount = count)
    }

    fun updateTimerPerQuestion(seconds: Int) {
        _uiState.value = _uiState.value.copy(selectedTimerPerQuestion = seconds)
    }

    fun startQuiz() {
        val settings = QuizSettings(
            numberOfQuestions = _uiState.value.selectedQuestionCount,
            timerPerQuestion = _uiState.value.selectedTimerPerQuestion
        )

        selectedQuestions = getQuizQuestions("cn", settings.numberOfQuestions)
        userAnswers.clear()
        startTime = System.currentTimeMillis()

        _uiState.value = _uiState.value.copy(
            showSettingsDialog = false,
            currentQuestionIndex = 0,
            quizSettings = settings,
            questions = selectedQuestions,
            isQuizStarted = true,
            timeRemaining = settings.timerPerQuestion,
            showResult = false
        )

        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.isQuizStarted && !_uiState.value.showResult) {
                delay(1000)
                val currentTime = _uiState.value.timeRemaining
                if (currentTime > 0) {
                    _uiState.value = _uiState.value.copy(timeRemaining = currentTime - 1)
                } else {
                    // Time's up - mark as incorrect if no answer selected
                    val currentIndex = _uiState.value.currentQuestionIndex
                    if (!userAnswers.containsKey(currentIndex)) {
                        // Mark as incorrect by storing an invalid answer index
                        userAnswers[currentIndex] = -1
                    }
                    // Move to next question or finish
                    if (_uiState.value.currentQuestionIndex < selectedQuestions.size - 1) {
                        nextQuestion()
                    } else {
                        finishQuiz()
                    }
                }
            }
        }
    }

    fun selectAnswer(answerIndex: Int) {
        val currentIndex = _uiState.value.currentQuestionIndex
        userAnswers[currentIndex] = answerIndex
        _uiState.value = _uiState.value.copy(selectedAnswer = answerIndex)
    }

    fun nextQuestion() {
        val nextIndex = _uiState.value.currentQuestionIndex + 1
        if (nextIndex < selectedQuestions.size) {
            _uiState.value = _uiState.value.copy(
                currentQuestionIndex = nextIndex,
                timeRemaining = _uiState.value.quizSettings.timerPerQuestion,
                selectedAnswer = userAnswers[nextIndex] ?: -1
            )
            // Restart timer for new question
            startTimer()
        } else {
            finishQuiz()
        }
    }

    fun previousQuestion() {
        val prevIndex = _uiState.value.currentQuestionIndex - 1
        if (prevIndex >= 0) {
            _uiState.value = _uiState.value.copy(
                currentQuestionIndex = prevIndex,
                timeRemaining = _uiState.value.quizSettings.timerPerQuestion,
                selectedAnswer = userAnswers[prevIndex] ?: -1
            )
            // Restart timer for previous question
            startTimer()
        }
    }

    fun finishQuiz() {
        timerJob?.cancel()
        timerJob = null
        
        val timeTaken = (System.currentTimeMillis() - startTime) / 1000
        viewModelScope.launch {
            val result = completeQuiz(
                quizId = "cn",
                quizName = "CN Quiz",
                questions = selectedQuestions,
                userAnswers = userAnswers.toMap(),
                timeTakenSeconds = timeTaken
            )
            _uiState.value = _uiState.value.copy(
                isQuizStarted = false,
                showResult = true,
                quizResult = result
            )
        }
    }

    fun resetQuiz() {
        timerJob?.cancel()
        timerJob = null
        _uiState.value = CNQuizUiState()
        userAnswers.clear()
    }
    
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

data class CNQuizUiState(
    val showSettingsDialog: Boolean = true,
    val selectedQuestionCount: Int = 10,
    val selectedTimerPerQuestion: Int = 30,
    val quizSettings: QuizSettings = QuizSettings(10, 30),
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val isQuizStarted: Boolean = false,
    val timeRemaining: Int = 30,
    val selectedAnswer: Int = -1,
    val showResult: Boolean = false,
    val quizResult: QuizResult? = null
)

package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

class QuizActivity : AppCompatActivity() {

    var questions: TextView? = null
    var question: TextView? = null

    var option1: AppCompatButton? = null
    var option2: AppCompatButton? = null
    var option3: AppCompatButton? = null
    var option4: AppCompatButton? = null

    var nextBtn: AppCompatButton? = null

    var quizTimer: Timer? = null
    var mins = 1;
    var seconds = 0;
    var questionsList: MutableList<Question> = ArrayList<Question>()
    var currentQuestion = 0
    var isAnswerSelected = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val getTopicName = intent.getStringExtra("selectedTopicName")

        val backBtn = findViewById<ImageView>(R.id.backBtn)
        val timer = findViewById<TextView>(R.id.timer)

        startTimer(timer)
        val topicSelection = findViewById<TextView>(R.id.topicSelection)
        topicSelection.setText(getTopicName)

        questions = findViewById(R.id.numOfQuestion)
        question = findViewById(R.id.question)

        option1 = findViewById(R.id.answer1)
        option2 = findViewById(R.id.answer2)
        option3 = findViewById(R.id.answer3)
        option4 = findViewById(R.id.answer4)
        nextBtn = findViewById(R.id.nextBtn)

        questionsList = QuizBank.getQuestion(topicSelection.text as String)
        if (questionsList.size == 0) {
            Toast.makeText(this@QuizActivity, "It not exist!", Toast.LENGTH_LONG).show()
            quizTimer!!.purge()
            quizTimer!!.cancel()

            startActivity(Intent(this@QuizActivity, MainActivity::class.java))
            finish()
        }

        loadCurrentQuestion()

        option1!!.setOnClickListener {
            if (!isAnswerSelected) answerSelected(option1!!)
        }
        option4!!.setOnClickListener {
            if (!isAnswerSelected) answerSelected(option4!!)
        }
        option3!!.setOnClickListener {
            if (!isAnswerSelected) answerSelected(option3!!)
        }
        option2!!.setOnClickListener {
            if (!isAnswerSelected) answerSelected(option2!!)
        }

        backBtn.setOnClickListener {
            quizTimer!!.purge()
            quizTimer!!.cancel()

            startActivity(Intent(this@QuizActivity, MainActivity::class.java))
            finish()
        }

        nextBtn!!.setOnClickListener {
            if (isAnswerSelected) {
                if (currentQuestion < questionsList.size - 1) {

                    isAnswerSelected = false
                    currentQuestion++
                    loadCurrentQuestion()
                } else {
                    moveToQuizResult()
                }
            } else {
                Toast.makeText(this@QuizActivity, "Please select the answer first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun moveToQuizResult() {
        val intent = Intent(this@QuizActivity, QuizResult::class.java)
        intent.putExtra("correct", getCorrectAnswers())
        intent.putExtra("incorrect", getInCorrectAnswers())
        intent.putExtra("questions", questionsList.size)
        quizTimer!!.purge()
        quizTimer!!.cancel()
        startActivity(intent)
        finish()
    }

    private fun answerSelected(optionBtn: AppCompatButton) {
        isAnswerSelected = true
        questionsList.get(currentQuestion).userSelectedAnswer = optionBtn.text.toString()
        when (optionBtn.text) {
            questionsList.get(currentQuestion).answer -> optionBtn.setBackgroundResource(R.drawable.round_back_10_green)
            else -> {
                optionBtn.setBackgroundResource(R.drawable.round_back_10_red)
                when (questionsList.get(currentQuestion).answer) {
                    option1!!.text -> option1!!.setBackgroundResource(R.drawable.round_back_10_green)
                    option2!!.text -> option2!!.setBackgroundResource(R.drawable.round_back_10_green)
                    option3!!.text -> option3!!.setBackgroundResource(R.drawable.round_back_10_green)
                    option4!!.text -> option4!!.setBackgroundResource(R.drawable.round_back_10_green)
                }
            }
        }
    }

    private fun loadCurrentQuestion() {
        questions!!.text = (currentQuestion + 1).toString() +  "/" + questionsList.size
        question!!.text = questionsList.get(currentQuestion).question
        option1!!.text = questionsList.get(currentQuestion).option1
        option2!!.text = questionsList.get(currentQuestion).option2
        option3!!.text = questionsList.get(currentQuestion).option3
        option4!!.text = questionsList.get(currentQuestion).option4
        option1!!.setBackgroundResource(R.drawable.round_back_10)
        option2!!.setBackgroundResource(R.drawable.round_back_10)
        option3!!.setBackgroundResource(R.drawable.round_back_10)
        option4!!.setBackgroundResource(R.drawable.round_back_10)
    }

    fun startTimer(timerTextView: TextView) {
        quizTimer = Timer()
        quizTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (seconds == 0 && mins > 0) {
                    mins--
                    seconds = 59
                } else if (seconds == 0 && mins <= 0) {
                    Toast.makeText(this@QuizActivity, "Time over", Toast.LENGTH_SHORT).show()
                    moveToQuizResult()
                } else seconds--

                runOnUiThread {
                    val timeText = String.format(Locale.getDefault(), "%02d:%02d", mins, seconds)
                    timerTextView.text = timeText
                }
            }

        }, 1000, 1000) ?: Toast.makeText(this@QuizActivity, "Something has problem", Toast.LENGTH_SHORT).show()
    }

    fun getCorrectAnswers(): Int {
        var correctAnswers = 0;
        for (question in questionsList) {
            if (question.userSelectedAnswer.equals(question.answer)) {
                correctAnswers++
            }
        }
        return correctAnswers
    }

    fun getInCorrectAnswers(): Int {
        var inCorrectAnswers = 0;
        for (question in questionsList) {
            if (!question.userSelectedAnswer.equals(question.answer)) {
                inCorrectAnswers++
            }
        }
        return inCorrectAnswers
    }

    override fun onBackPressed() {
        quizTimer!!.purge()
        quizTimer!!.cancel()

        startActivity(Intent(this@QuizActivity, MainActivity::class.java))
        finish()

    }

}
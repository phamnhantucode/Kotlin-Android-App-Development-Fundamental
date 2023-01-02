package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class QuizResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        val questions = findViewById<TextView>(R.id.questions)
        val correct = findViewById<TextView>(R.id.correct)
        val incorrect = findViewById<TextView>(R.id.incorrect)
        questions.text = "Question: " + intent.getIntExtra("questions", 0);
        correct.text = "Correct: " + intent.getIntExtra("correct", 0)
        incorrect.text = "Incorrect: " + intent.getIntExtra("incorrect", 0)

        val newQuizBtn =  findViewById<AppCompatButton>(R.id.startNewQuiz)
        newQuizBtn.setOnClickListener {
            startActivity(Intent(this@QuizResult, MainActivity::class.java))
        }
    }
}
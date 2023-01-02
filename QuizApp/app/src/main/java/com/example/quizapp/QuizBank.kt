package com.example.quizapp

class QuizBank {

    companion object {

        private fun javaQuestion(): ArrayList<Question> {
            var quesList: ArrayList<Question> = ArrayList()
            quesList.add(Question("Bảo đẹp trai không?", "Có", "Không", "Không biết", "Không muốn trả lời", "Có", ""))
            quesList.add(Question("1 + 1 = 3?", "Đúng", "Không", "Không biết", "Không muốn trả lời", "Đúng", ""))
            return  quesList
        }

        fun getQuestion(selection: String): ArrayList<Question> = when (selection) {
            "java" -> javaQuestion()
            else -> ArrayList()
        }
    }


}
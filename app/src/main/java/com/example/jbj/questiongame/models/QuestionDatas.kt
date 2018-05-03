package com.example.jbj.questiongame.models

data class QuestionDatas(
        val questiondata :QuestionData
)
data class QuestionData(
        val id: Int,
        val question: String,
        val answer_a: String,
        val answer_b: String,
        val answer_c: String,
        val answer_d: String,
        val correct: String
)
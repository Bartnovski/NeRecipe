package ru.netology.nerecipe.models

data class Step(
    val id : Long,
    val idToRecipe : Long,
    val stepContent : String,
    val stepImagePath :String?
)
package ru.netology.nerecipe.models

data class Step(
    val stepId : Long,
    val idToRecipe : Long,
    val positionInRecipe : Int,
    val stepContent : String,
    val stepImagePath :String?
)
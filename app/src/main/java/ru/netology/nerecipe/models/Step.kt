package ru.netology.nerecipe.models

data class Step(
    val id : Long,
    val idToRecipe : Long,
    val positionInRecipe : Int,
    val stepContent : String,
    val stepImagePath :String?
)
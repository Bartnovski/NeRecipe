package ru.netology.nerecipe.models

import android.net.Uri
import androidx.room.Embedded

data class Step(
    val id : Int,
    val idToRecipe : Int,
    val stepContent : String,
    val stepImagePath : String?
)
package ru.netology.nerecipe.models

import android.net.Uri
import androidx.room.Embedded

data class Step(
    val id : Long,
    val idToRecipe : Long,
    val stepContent : String,
    val stepImagePath : String?
)
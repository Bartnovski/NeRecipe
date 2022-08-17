package ru.netology.nerecipe.models

import android.net.Uri

data class Step(
    val id : Int,
    val stepContent : String,
    val stepImagePath : Uri?
)
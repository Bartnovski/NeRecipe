package ru.netology.nerecipe.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps")
data class StepEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "stepId")
    val stepId: Long,

    @ColumnInfo(name = "idToRecipe")
    val idToRecipe: Long,

    @ColumnInfo(name = "positionInRecipe")
    val positionInRecipe: Int,

    @ColumnInfo(name = "stepContent")
    val stepContent : String,

    @ColumnInfo(name = "stepImagePath")
    val stepImagePath : String?,

)
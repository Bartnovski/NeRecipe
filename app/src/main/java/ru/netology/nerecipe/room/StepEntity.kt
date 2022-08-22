package ru.netology.nerecipe.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "steps")
class StepEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "idToRecipe")
    val idToRecipe: Long,

    @ColumnInfo(name = "stepContent")
    val stepContent : String,

    @ColumnInfo(name = "stepImagePath")
    val stepImagePath : String?,

)
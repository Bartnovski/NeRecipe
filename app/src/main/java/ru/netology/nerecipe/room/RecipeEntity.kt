package ru.netology.nerecipe.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
class RecipeEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val recipeId: Long,

    @ColumnInfo(name = "recipeName")
    val recipeName: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "recipeGroup")
    val category: String,

    @ColumnInfo(name = "recipeImagePath")
    val recipeImagePath: String?,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean,

)
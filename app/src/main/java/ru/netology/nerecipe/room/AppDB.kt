package ru.netology.nerecipe.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RecipeEntity::class,StepEntity::class],
    version = 1
)
abstract class AppDB : RoomDatabase() {
    abstract val dao: DAO

    companion object {
        @Volatile
        private var instance: AppDB? = null

        fun getInstance(context: Context) : AppDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context,AppDB::class.java,"recipes.db")
                .allowMainThreadQueries().build()
    }
}
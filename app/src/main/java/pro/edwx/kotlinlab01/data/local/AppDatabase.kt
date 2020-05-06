package pro.edwx.kotlinlab01.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pro.edwx.kotlinlab01.data.local.dao.CategoryDao
import pro.edwx.kotlinlab01.data.local.entity.Category

@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
}
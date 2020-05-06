package pro.edwx.kotlinlab01

import android.app.Application
import androidx.room.Room
import pro.edwx.kotlinlab01.data.local.AppDatabase

class App : Application() {

    companion object{
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this.applicationContext, AppDatabase::class.java, "lab01_db"
        ).build()
    }
}
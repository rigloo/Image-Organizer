package com.rigosapps.imageorganizer.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rigosapps.imageorganizer.model.ImageItem


// 1
@Database(entities = [ImageItem::class], version = 1, exportSchema = false)
abstract class ImageItemDatabase : RoomDatabase() {
    // 2
    abstract fun imageItemDao(): ImageItemDao

    // 3
    companion object {
        // 4
        @Volatile
        private var instance: ImageItemDatabase? = null

        // 5
        fun getInstance(context: Context): ImageItemDatabase {
            if (instance == null) {
                // 6
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ImageItemDatabase::class.java,
                    "imageItem_database"
                ).build()
            }
// 7
            return instance as ImageItemDatabase
        }
    }
}

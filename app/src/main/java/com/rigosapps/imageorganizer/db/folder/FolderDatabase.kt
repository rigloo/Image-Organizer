package com.rigosapps.imageorganizer.db.folder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rigosapps.imageorganizer.model.Folder


// 1
@Database(entities = [Folder::class], version = 1, exportSchema = false)
abstract class FolderDatabase : RoomDatabase() {
    // 2
    abstract fun folderItemDao(): FolderDao

    // 3
    companion object {
        // 4
        @Volatile
        private var instance: FolderDatabase? = null

        // 5
        fun getInstance(context: Context): FolderDatabase {
            if (instance == null) {
                // 6
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    FolderDatabase::class.java,
                    "folder_database"
                ).build()
            }
// 7
            return instance as FolderDatabase
        }
    }
}

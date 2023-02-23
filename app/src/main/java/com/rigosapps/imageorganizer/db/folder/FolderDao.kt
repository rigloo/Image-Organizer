package com.rigosapps.imageorganizer.db.folder

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rigosapps.imageorganizer.model.ImageItem
import com.rigosapps.imageorganizer.model.Folder

@Dao
interface FolderDao {

    @Query("SELECT * FROM Folder")
    fun loadAll(): LiveData<List<Folder>>

    @Query("SELECT * FROM Folder WHERE key = :folderId")
    fun loadFolder(folderId: Long): Folder

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFolder(folder: Folder): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateFolder(folder: Folder)

    @Delete
    fun deleteFolder(folder: Folder)

}
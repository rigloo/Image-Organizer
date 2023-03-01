package com.rigosapps.imageorganizer.db.imageItem

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rigosapps.imageorganizer.model.ImageItem

/*

The DAO for the ImageItem database. Provides methods to access , write, update and delete from the ImageItem db.
 */

@Dao
interface ImageItemDao {

    @Query("SELECT * FROM ImageItem")
    fun loadAll(): LiveData<List<ImageItem>>

    @Query("SELECT * FROM ImageItem WHERE key = :imageItemId")
    fun loadImageItem(imageItemId: Long): ImageItem

    @Query("SELECT * FROM ImageItem WHERE folderId = :folderId")
    fun getImageItemsByFolder(folderId: Long): LiveData<List<ImageItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertImageItem(imageItem: ImageItem): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateImageItem(imageItem: ImageItem)

    @Delete
    fun deleteImageItem(imageItem: ImageItem)

    @Query("DELETE FROM ImageItem WHERE folderId = :folderId")
    fun deleteByFolderId(folderId: Long)



}
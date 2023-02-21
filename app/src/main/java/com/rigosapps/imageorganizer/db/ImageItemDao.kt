package com.rigosapps.imageorganizer.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rigosapps.imageorganizer.model.ImageItem

@Dao
interface ImageItemDao {

    @Query("SELECT * FROM ImageItem")
    fun loadAll(): LiveData<List<ImageItem>>

    @Query("SELECT * FROM ImageItem WHERE key = :imageItemId")
    fun loadImageItem(imageItemId: Long): ImageItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertImageItem(imageItem: ImageItem): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateImageItem(imageItem: ImageItem)

    @Delete
    fun deleteImageItem(imageItem: ImageItem)

}
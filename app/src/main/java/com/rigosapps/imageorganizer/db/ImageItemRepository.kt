package com.rigosapps.imageorganizer.db

import androidx.lifecycle.LiveData
import com.rigosapps.imageorganizer.model.ImageItem

class ImageItemRepository(private val imageItemDao: ImageItemDao) {

    val readAllData: LiveData<List<ImageItem>> = imageItemDao.loadAll()

    suspend fun addImageItem(imageItem: ImageItem): Long {
        return imageItemDao.insertImageItem(imageItem)
    }

    suspend fun updateImageItem(imageItem: ImageItem) {
        imageItemDao.updateImageItem(imageItem)
    }

    suspend fun deleteImageItem(imageItem: ImageItem) {
        imageItemDao.deleteImageItem(imageItem)
    }

    suspend fun getImageItem(id: Long): ImageItem {
        return imageItemDao.loadImageItem(id)
    }
}
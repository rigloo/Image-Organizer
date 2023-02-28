package com.rigosapps.imageorganizer.db.imageItem

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

    suspend fun getImagesByFolder(id: Long): LiveData<List<ImageItem>> {
        return imageItemDao.getImageItemsByFolder(id)
    }

    suspend fun deleteImagesByFolder(folderId: Long) {
        imageItemDao.deleteByFolderId(folderId)
    }
}
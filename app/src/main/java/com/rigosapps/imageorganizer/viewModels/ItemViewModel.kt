package com.rigosapps.imageorganizer.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rigosapps.imageorganizer.db.imageItem.ImageItemDatabase
import com.rigosapps.imageorganizer.db.imageItem.ImageItemRepository
import com.rigosapps.imageorganizer.model.ImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class ItemViewModel(application: Application) : AndroidViewModel(application) {


    var readData: LiveData<List<ImageItem>>
    private val repository: ImageItemRepository

    //lateinit var dataByFolder: LiveData<List<ImageItem>>
    var currentFolderId: Long


    init {

        val imageItemDao = ImageItemDatabase.getInstance(application).imageItemDao()
        repository = ImageItemRepository(imageItemDao)
        readData = repository.readAllData

        currentFolderId = -1

    }


    fun addItem(imageItem: ImageItem): Long {
        var id: Long = -1


        val job = viewModelScope.async(Dispatchers.IO) {

            id = repository.addImageItem(imageItem)

        }

        runBlocking {
            job.join()
        }
        Timber.e("New id $id")
        return id!!


    }

    fun getAllData() {


        currentFolderId = -1
        readData = repository.readAllData
        Timber.e("Got all data with values ${readData.value}")

    }

    fun getEntriesByFolder(folderId: Long) {
        currentFolderId = folderId

        val job = viewModelScope.async(Dispatchers.IO) {

            readData = repository.getImagesByFolder(folderId)

        }
        runBlocking {
            job.join()
        }

        Timber.e("Got all data with folder $folderId and value ${readData.value}")


    }


    fun updateImageItem(imageItem: ImageItem) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.updateImageItem(imageItem)
        }

    }

    fun deleteImageItem(imageItem: ImageItem) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.deleteImageItem(imageItem)
        }


    }

    fun deleteImageItemByFolder(folderId: Long) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.deleteImagesByFolder(folderId)
        }


    }

    fun getImageItem(id: Long): ImageItem {
        lateinit var imageItem: ImageItem
        val job = viewModelScope.launch(Dispatchers.IO) {

            imageItem = repository.getImageItem(id)
        }

        runBlocking {
            job.join()
        }

        return imageItem


    }


}
package com.rigosapps.imageorganizer.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rigosapps.imageorganizer.db.ImageItemDatabase
import com.rigosapps.imageorganizer.db.ImageItemRepository
import com.rigosapps.imageorganizer.model.ImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.io.FileOutputStream

class ItemViewModel(application: Application) : AndroidViewModel(application) {


    val readAllData: LiveData<List<ImageItem>>
    private val repository: ImageItemRepository


    init {

        val imageItemDao = ImageItemDatabase.getInstance(application).imageItemDao()
        repository = ImageItemRepository(imageItemDao)
        readAllData = repository.readAllData

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
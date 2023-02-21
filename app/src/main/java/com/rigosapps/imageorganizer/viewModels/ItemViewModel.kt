package com.rigosapps.imageorganizer.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rigosapps.imageorganizer.db.ImageItemDatabase
import com.rigosapps.imageorganizer.db.ImageItemRepository
import com.rigosapps.imageorganizer.model.ImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var onListAdded: (() -> Unit)
    lateinit var onListUpdated: ((Int) -> Unit)

    val _itemList = mutableListOf<ImageItem>()

    val readAllData: LiveData<List<ImageItem>>
    private val repository: ImageItemRepository


    init {

        val imageItemDao = ImageItemDatabase.getInstance(application).imageItemDao()
        repository = ImageItemRepository(imageItemDao)
        readAllData = repository.readAllData

    }


    fun addItem(imageItem: ImageItem) {
//        _itemList.add(imageItem)
//        onListAdded.invoke()

        viewModelScope.launch(Dispatchers.IO) {

            repository.addImageItem(imageItem)
        }


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


}
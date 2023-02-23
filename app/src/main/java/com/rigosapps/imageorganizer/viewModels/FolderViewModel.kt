package com.rigosapps.imageorganizer.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rigosapps.imageorganizer.db.folder.FolderDatabase
import com.rigosapps.imageorganizer.db.folder.folderRepository
import com.rigosapps.imageorganizer.model.Folder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FolderViewModel(application: Application) : AndroidViewModel(application) {


    val readAllData: LiveData<List<Folder>>
    private val repository: folderRepository


    init {

        val folderItemDao = FolderDatabase.getInstance(application).folderItemDao()
        repository = folderRepository(folderItemDao)
        readAllData = repository.readAllData

    }


    fun addItem(folder: Folder): Long {
        var id: Long = -1


        val job = viewModelScope.async(Dispatchers.IO) {

            id = repository.addFolder(folder)

        }

        runBlocking {
            job.join()
        }
        Timber.e("New id $id")
        return id!!


    }


    fun updateImageItem(folder: Folder) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.updateFolder(folder)
        }

    }

    fun deleteImageItem(imageItem: Folder) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.deleteFolder(imageItem)
        }


    }

    fun getImageItem(id: Long): Folder {
        lateinit var folder: Folder
        val job = viewModelScope.launch(Dispatchers.IO) {

            folder = repository.getFolderItem(id)
        }

        runBlocking {
            job.join()
        }

        return folder


    }


}
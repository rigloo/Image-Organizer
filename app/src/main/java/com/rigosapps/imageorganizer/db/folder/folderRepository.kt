package com.rigosapps.imageorganizer.db.folder

import androidx.lifecycle.LiveData
import com.rigosapps.imageorganizer.model.Folder
import com.rigosapps.imageorganizer.model.ImageItem

class folderRepository(private val folderDao: FolderDao) {

    val readAllData: LiveData<List<Folder>> = folderDao.loadAll()

    suspend fun addFolder(folder: Folder): Long {
        return folderDao.insertFolder(folder)
    }


    suspend fun deleteFolder(folder: Folder) {
        folderDao.deleteFolder(folder)
    }

    suspend fun getFolderItem(id: Long): Folder {
        return folderDao.loadFolder(id)
    }

    suspend fun updateFolder(folder: Folder) {
        return folderDao.updateFolder(folder)
    }
}
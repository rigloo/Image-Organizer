package com.rigosapps.imageorganizer.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ItemViewModel : ViewModel() {

    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val current = formatter.format(time)

    lateinit var onListAdded: (() -> Unit)
    lateinit var onListUpdated: ((Int) -> Unit)

    val _itemList = mutableListOf<ImageItem>()


    fun addItem(imageItem: ImageItem) {
        _itemList.add(imageItem)
        onListAdded.invoke()
    }

    fun updateList(imageItem: ImageItem) {

        val index = _itemList.indexOfFirst { it.key == imageItem.key }
        _itemList[index] = imageItem
        onListUpdated.invoke(index)

    }

    fun refreshLists(index: Int) {
        //you would clear the list then retrieve all from sharedPreferences or something

    }


}
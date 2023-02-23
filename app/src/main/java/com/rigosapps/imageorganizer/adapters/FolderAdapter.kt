package com.rigosapps.imageorganizer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.rigosapps.imageorganizer.R
import com.rigosapps.imageorganizer.model.Folder
import timber.log.Timber

class FolderAdapter(val onClick: (id: Long) -> Unit) :
    RecyclerView.Adapter<FolderAdapter.ViewHolder>() {
    private var folders = emptyList<Folder>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView
        val deleteButton: Button


        init {

            titleText = view.findViewById(R.id.folder_name)
            deleteButton = view.findViewById(R.id.delete_button)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }

    fun getItembyPosition(index: Int): Folder {

        return folders[index]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.titleText.text = folders[position].folderName


        holder.titleText.setOnClickListener() {


            Timber.e("Navigate from home to Detail.")
            onClick(folders[position].key)

        }

        holder.deleteButton.setOnClickListener {


        }
    }


    override fun getItemCount(): Int {
        return folders.size
    }

    fun setData(folders: List<Folder>) {

        this.folders = folders
        notifyDataSetChanged()

    }

}
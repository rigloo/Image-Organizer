package com.rigosapps.imageorganizer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*

Model data class file for the Folder. The only information a folder contains is a name.
Each ImageItem entry is a associated with a folderId, which is the primary key to each folder.
(Unless you add to the All folder, in which case it would have a folderIf of -1.

 */

@Entity
data class Folder(

    @PrimaryKey(autoGenerate = true) val key: Long,

    @ColumnInfo(name = "folder_name") var folderName: String?,


    )




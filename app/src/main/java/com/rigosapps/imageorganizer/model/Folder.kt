package com.rigosapps.imageorganizer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Folder(

    @PrimaryKey(autoGenerate = true) val key: Long,

    @ColumnInfo(name = "folder_name") var folderName: String?,


    )




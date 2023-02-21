package com.rigosapps.imageorganizer.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.rigosapps.imageorganizer.helpers.TimeHelper
import java.util.*

import androidx.room.PrimaryKey

@Entity
data class ImageItem(
    @PrimaryKey(autoGenerate = true)
    val key: Long,

    @ColumnInfo(name = "image_path")
    var imagePath: String?,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "description")
    var description: String
) :
    Parcelable {

    constructor(source: Parcel) : this(
        source.readLong()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(key)
        if (imagePath == null)
            parcel.writeString("null")
        else {
            parcel.writeString(imagePath)
        }
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<ImageItem> {
        override fun createFromParcel(parcel: Parcel): ImageItem {
            return ImageItem(parcel)
        }

        override fun newArray(size: Int): Array<ImageItem?> {
            return arrayOfNulls(size)
        }
    }

}

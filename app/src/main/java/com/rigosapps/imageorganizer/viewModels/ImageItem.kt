package com.rigosapps.imageorganizer.viewModels

import android.os.Parcel
import android.os.Parcelable
import com.rigosapps.imageorganizer.helpers.TimeHelper
import java.util.*


data class ImageItem(
    val key: String,
    var imagePath: String?,
    val title: String,
    val date: Date,
    var description: String
) :
    Parcelable {

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        TimeHelper.getDateFromString(source.readString()!!),
        source.readString()!!

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        if (imagePath == null)
            parcel.writeString("null")
        else {
            parcel.writeString(imagePath)
        }
        parcel.writeString(title)
        parcel.writeString(TimeHelper.getStringfromDate(date))
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

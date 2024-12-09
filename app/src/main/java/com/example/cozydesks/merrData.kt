package com.example.cozydesks

import android.os.Parcel
import android.os.Parcelable

data class merrData(val title:String = "",val subTitle:String = "",val city:String = "",
                    val address:String = "",val creatorID:String = "",
                    val linkToGroup:String = "",val description:String = "") : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(subTitle)
        parcel.writeString(city)
        parcel.writeString(address)
        parcel.writeString(creatorID)
        parcel.writeString(linkToGroup)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<merrData> {
        override fun createFromParcel(parcel: Parcel): merrData {
            return merrData(parcel)
        }

        override fun newArray(size: Int): Array<merrData?> {
            return arrayOfNulls(size)
        }
    }
}

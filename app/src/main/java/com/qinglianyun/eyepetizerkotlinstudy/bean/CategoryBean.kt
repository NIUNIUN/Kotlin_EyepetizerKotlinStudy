package com.tt.lvruheng.eyepetizer.mvp.model.bean

import android.os.Parcel
import android.os.Parcelable

data class CategoryBean(var id: Int, var name: String?,
                        var description: String?, var bgPicture: String?,
                        var bgColor: String?, var headerImage: String?) :Parcelable{
    /**
     * id : 36
     * name : 生活
     * alias : null
     * description : 匠心、健康、生活感悟
     * bgPicture : http://img.kaiyanapp.com/924ebc6780d59925c8a346a5dafc90bb.jpeg
     * bgColor :
     * headerImage : http://img.kaiyanapp.com/a1a1bf7ed3ac906ee4e8925218dd9fbe.png
     */
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(bgPicture)
        parcel.writeString(bgColor)
        parcel.writeString(headerImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryBean> {
        override fun createFromParcel(parcel: Parcel): CategoryBean {
            return CategoryBean(parcel)
        }

        override fun newArray(size: Int): Array<CategoryBean?> {
            return arrayOfNulls(size)
        }
    }
}


package com.example.android.todo.api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


class TodoResponse() : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeBoolean(done)
        parcel.writeBoolean(archived)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
        parcel.writeString(v)

    }

    override fun describeContents(): Int {
        return 0
    }

    @SerializedName("_id")
    @Expose
    var id:String?=null
    @SerializedName("title")
    @Expose
    var title:String?=null
    @SerializedName("content")
    @Expose
    var content:String?=null
    @SerializedName("done")
    @Expose
    var done:Boolean=false
    @SerializedName("archived")
    @Expose
    var archived:Boolean=false
    @SerializedName("createdAt")
    @Expose
    var createdAt:String?=null
    @SerializedName("updatedAt")
    @Expose
    var updatedAt:String?=null
    @SerializedName("__v")
    @Expose
    var v:String?=null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        title = parcel.readString()
        content = parcel.readString()
        done = parcel.readValue(Boolean::class.java.classLoader) as Boolean
        archived = parcel.readValue(Boolean::class.java.classLoader) as Boolean
        createdAt = parcel.readString()
        updatedAt = parcel.readString()
        v = parcel.readString()
    }


//
//    @SerializedName("userId")
//    var userId:Int? = null
//    @SerializedName("id")
//    var id:Int? = null
//    @SerializedName("title")
//    var title:String? = null
//    @SerializedName("completed")
//    var completed:Boolean? = null
    companion object CREATOR : Parcelable.Creator<TodoResponse> {
        override fun createFromParcel(parcel: Parcel): TodoResponse {
            return TodoResponse(parcel)
        }

        override fun newArray(size: Int): Array<TodoResponse?> {
            return arrayOfNulls(size)
        }
    }
}
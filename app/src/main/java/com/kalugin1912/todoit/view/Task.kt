package com.kalugin1912.todoit.view

import android.os.Parcel
import android.os.Parcelable

data class Task(
    val id: Int,
    val name: String,
    val description: String,
    val priority: Priority,
    val isCompleted: Boolean = false,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        Priority.valueOf(parcel.readString() ?: Priority.LOW.name),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(priority.name)
        parcel.writeByte(if (isCompleted) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}

enum class Priority {
    HIGH,
    MEDIUM,
    LOW,
}
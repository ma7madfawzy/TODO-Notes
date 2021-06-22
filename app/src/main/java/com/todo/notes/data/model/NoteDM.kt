package com.todo.notes.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "user_notes",
    indices = [Index(value = ["title", "description"], unique = true)]
)
data class NoteDM(
    val title: String?,
    val description: String?,
    val date: String?
) : Parcelable {
    @NotNull
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        id = parcel.readValue(Long::class.java.classLoader) as? Long
    }

    constructor(title: String?, desc: String?, date: String?, id: Long?) : this(title, desc, date) {
        this.id = id
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(date)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteDM> {
        override fun createFromParcel(parcel: Parcel): NoteDM {
            return NoteDM(parcel)
        }

        override fun newArray(size: Int): Array<NoteDM?> {
            return arrayOfNulls(size)
        }
    }
}
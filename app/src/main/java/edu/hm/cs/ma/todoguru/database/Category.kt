package edu.hm.cs.ma.todoguru.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "category")
@Parcelize
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "description")
    var description: String
) : Serializable, Parcelable {

    constructor(description: String) : this(0, description)
}

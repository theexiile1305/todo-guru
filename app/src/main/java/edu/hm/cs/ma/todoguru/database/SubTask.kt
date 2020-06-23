package edu.hm.cs.ma.todoguru.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "sub_task")
@Parcelize
data class SubTask(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "description")
    var description: String
) : Serializable, Parcelable {
    constructor(subTask: SubTask) : this(subTask.description)
    constructor(description: String) : this(0, description)

    override fun toString(): String {
        return super.toString()
    }
}

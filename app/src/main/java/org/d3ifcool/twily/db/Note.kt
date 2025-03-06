package org.d3ifcool.twily.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.d3ifcool.twily.util.Date

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val details : String,
    val date : String,
)

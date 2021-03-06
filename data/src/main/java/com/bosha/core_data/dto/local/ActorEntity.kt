package com.bosha.core_data.dto.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actors")
data class ActorEntity(

    @PrimaryKey
    val id: Int,

    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,
)
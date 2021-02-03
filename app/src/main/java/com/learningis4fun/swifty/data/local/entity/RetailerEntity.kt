package com.learningis4fun.swifty.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "retailerTable")
data class RetailerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "retailerId")
    val id: Int = 0,
    @ColumnInfo(name = "retailerName")
    val name: String = "",
    @ColumnInfo(name = "retailerLocation")
    val location: String = ""
)
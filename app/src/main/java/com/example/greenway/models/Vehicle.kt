package com.example.greenway.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class FuelType {
    GASOLINE,
    DIESEL
}

enum class TransmissionType {
    MANUAL,
    AUTO
}

@Entity(tableName = "vehicles_table")
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo val name: String,
    @ColumnInfo val fuelType: FuelType,
    @ColumnInfo val transmissionType: TransmissionType,
    @ColumnInfo val maxRPM : Int,
    @ColumnInfo var isActive : Boolean = false
) {


}

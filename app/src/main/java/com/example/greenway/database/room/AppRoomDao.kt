package com.example.greenway.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.greenway.models.Vehicle


@Dao
interface AppRoomDao {
    @Query("SELECT * FROM vehicles_table")
    fun getAllVehicles():LiveData<List<Vehicle>>

    @Query("SELECT * FROM vehicles_table WHERE isActive = 1")
    fun getActiveVehicle() : LiveData<Vehicle>

    @Query("UPDATE vehicles_table SET isActive = 0 WHERE isActive = 1")
    fun changeActiveState()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vehicle: Vehicle)

    @Update
    suspend fun update(vehicle: Vehicle)

    @Delete
    suspend fun delete(vehicle: Vehicle)


}
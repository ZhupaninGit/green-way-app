package com.example.greenway.database

import androidx.lifecycle.LiveData
import com.example.greenway.models.Vehicle

interface DatabaseRepository {
    val allVehicles: LiveData<List<Vehicle>>
    val activeVehicle : LiveData<Vehicle>
    suspend fun insert(vehicle: Vehicle,onSuccess:()->Unit)
    suspend fun delete(vehicle: Vehicle,onSuccess:()->Unit)
    suspend fun update(vehicle: Vehicle,onSuccess: () -> Unit)
    suspend fun changeActiveState(onSuccess: () -> Unit)

}
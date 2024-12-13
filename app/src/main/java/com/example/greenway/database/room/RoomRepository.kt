package com.example.greenway.database.room

import androidx.lifecycle.LiveData
import com.example.greenway.database.DatabaseRepository
import com.example.greenway.models.Vehicle

class RoomRepository(private val appRoomDao: AppRoomDao) : DatabaseRepository {

    override val allVehicles: LiveData<List<Vehicle>>
        get() = appRoomDao.getAllVehicles()

    override val activeVehicle: LiveData<Vehicle>
        get() = appRoomDao.getActiveVehicle()


    override suspend fun changeActiveState(onSuccess: () -> Unit) {
        appRoomDao.changeActiveState()
        onSuccess()
    }

    override suspend fun insert(vehicle: Vehicle, onSuccess: () -> Unit) {
        appRoomDao.insert(vehicle)
        onSuccess()
    }

    override suspend fun delete(vehicle: Vehicle, onSuccess: () -> Unit) {
        appRoomDao.delete(vehicle)
        onSuccess()
    }

    override suspend fun update(vehicle: Vehicle, onSuccess: () -> Unit) {
        appRoomDao.update(vehicle)
        onSuccess()
    }
}
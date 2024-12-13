package com.example.greenway.screens.settings.vehicle.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.greenway.database.room.AppRoomDatabase
import com.example.greenway.database.room.RoomRepository
import com.example.greenway.models.Vehicle
import com.example.greenway.utils.REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application):AndroidViewModel(application) {
    private val dao = AppRoomDatabase.getInstance(application).getAppRoomDao()
    val allVehicles: LiveData<List<Vehicle>>

    init {
        REPOSITORY = RoomRepository(dao)
        allVehicles = REPOSITORY.allVehicles
    }

    fun updateActiveVehicle(newActiveVehicle: Vehicle,onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO){
                REPOSITORY.changeActiveState {
                    onSuccess()
                }
                REPOSITORY.update(newActiveVehicle){
                    onSuccess()
                }
        }
    }

    fun deleteVehicle(vehicle: Vehicle,onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.delete(vehicle){
                onSuccess()
            }
        }
    }
}
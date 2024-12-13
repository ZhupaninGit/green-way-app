package com.example.greenway.screens.settings.vehicle.add_new_vehicle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenway.models.Vehicle
import com.example.greenway.utils.REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewVehicleViewModel(application: Application) : AndroidViewModel(application) {
    fun insert(vehicle: Vehicle,onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.Main){
            REPOSITORY.insert(vehicle){
                onSuccess()
            }
        }
    }
}
package com.example.greenway.screens.settings

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.greenway.database.room.AppRoomDatabase
import com.example.greenway.database.room.RoomRepository
import com.example.greenway.models.Vehicle
import com.example.greenway.utils.APP_ACTIVITY
import com.example.greenway.utils.REPOSITORY


class SettingsFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppRoomDatabase.getInstance(application).getAppRoomDao()
    private var preferences : SharedPreferences? = null

    var activeVehicle : LiveData<Vehicle>

    init {
        REPOSITORY = RoomRepository(dao)
        activeVehicle = REPOSITORY.activeVehicle

        preferences = APP_ACTIVITY.getSharedPreferences("main_preference", Context.MODE_PRIVATE)
    }

    fun getActiveScannerName() : String{
        return preferences?.getString("name","").toString()
    }





}
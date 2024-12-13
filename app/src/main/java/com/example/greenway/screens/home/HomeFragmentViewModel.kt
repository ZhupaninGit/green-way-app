package com.example.greenway.screens.home

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bt_library.BluetoothController
import com.example.greenway.utils.APP_ACTIVITY

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application),BluetoothController.Listener {
    private var btController: BluetoothController
    private var btAdapter: BluetoothAdapter
    private var macAddress: String
    private val _connectionState = MutableLiveData(BluetoothState.NOT_CONNECTED)
    val connectionState: LiveData<BluetoothState> get() = _connectionState


    init {
        val bManager = APP_ACTIVITY.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = bManager.adapter
        btController = BluetoothController(btAdapter)
        val preferences = APP_ACTIVITY.getSharedPreferences("main_preference",Context.MODE_PRIVATE)
        macAddress = preferences?.getString("mac","")!!
    }

    fun connect(){
        btController.connect(macAddress ?: "",this)
        _connectionState.value = BluetoothState.CONNECTING
    }

    fun sendData(message: String){
        println("We are in viewModel")
        btController.sendMessage(message)
    }

    override fun onRecieve(msg: String) {
        APP_ACTIVITY.runOnUiThread {
            when(msg){
                BluetoothController.BLUETOOTH_CONNECTED ->{
                    println("connected")
                    _connectionState.value = BluetoothState.CONNECTED
                }
                BluetoothController.BLUETOOTH_NO_CONNECTED ->{
                    println("not connected")
                    _connectionState.value = BluetoothState.NOT_CONNECTED
                }
                else -> {
                    println(msg)
                }
            }
        }
    }

    enum class BluetoothState{
        NOT_CONNECTED,
        CONNECTING,
        CONNECTED
    }

}
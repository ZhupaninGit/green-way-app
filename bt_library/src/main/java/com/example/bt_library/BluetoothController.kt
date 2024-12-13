package com.example.bt_library

import android.bluetooth.BluetoothAdapter

class BluetoothController(val adapter: BluetoothAdapter) {
    private var connectThread : ConnectThread? = null

    fun connect(mac : String, listener: Listener){
        if(adapter.isEnabled && mac.isNotEmpty()){
            val device = adapter.getRemoteDevice(mac)
            connectThread = ConnectThread(device,listener)
            connectThread?.start()
        }
    }
    fun sendMessage(message:String){
        connectThread?.sendMessage(message = message)
    }

    companion object{
        const val BLUETOOTH_CONNECTED = "bluetooth_connected"
        const val BLUETOOTH_NO_CONNECTED = "bluetooth_no_connected"
    }

    interface Listener{
        fun onRecieve(msg : String){

        }
    }
}
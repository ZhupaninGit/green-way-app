package com.example.bt_library

import android.bluetooth.BluetoothDevice

data class ScannerDevice(
    val device: BluetoothDevice,
    val isChecked: Boolean

)

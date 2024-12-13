package com.example.bt_library

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import com.github.eltonvs.obd.connection.ObdDeviceConnection
import java.io.IOException
import java.util.UUID


class ConnectThread(private val device: BluetoothDevice,val listener: BluetoothController.Listener) : Thread() {
    private val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var mSocket :BluetoothSocket? = null
    private var obd : ObdDeviceConnection? = null
     init {

         try {
             mSocket = device.createRfcommSocketToServiceRecord(uuid)
         } catch (e : IOException){
             Log.d("my","error : $e")

         } catch (e: SecurityException){
             Log.d("my","error : $e")
         }
     }

    fun sendUDSCommands() {
        try {
            val commands = listOf(
                "ATZ",               // Reset settings
                "AT E0",             // Disable echo
                "AT L0",             // Disable line feed
                "AT SP 6",           // Set ISO 15765-4 CAN protocol (11 bit ID, 500 kbaud)
                "AT ST 10",          // Set timeout
                "AT CA F0",          // Clear any previously set address filters
                "AT AL",             // Allow long messages
                "AT SH 7E0",         // Set CAN ID for the engine
                "AT CRA 7E8",        // Set CAN Receive Address
                "AT FC SH 7E0",      // Set Flow Control Source Address
                "AT FC SD 30 00 00", // Set Flow Control Data
                "AT FC SM 1",        // Set Flow Control Mode
            )

            for (command in commands) {
                sendMessage("$command\r") // Append a carriage return
                Thread.sleep(50) // Small delay to ensure the device processes commands sequentially
            }
        } catch (e: Exception) {
            Log.d("ConnectThread", "Error sending UDS commands: $e")
        }
    }

    fun readMessage(){
        val byteArray = ByteArray(1024)
        while (true){
            try {
                val len = mSocket?.inputStream?.read(byteArray)
                val message = String(byteArray,0,len ?: 0)
                listener.onRecieve(message)
            } catch (e : IOException){
                throw e
            }
        }
    }

    fun sendMessage(message: String){
        println("We are in connectThread")
        println("our message is $message")
 
        try {
            mSocket?.outputStream?.write(message.toByteArray())
        } catch (e: IOException){

        }
    }



    override fun run() {
        try {
            mSocket?.connect()
            listener.onRecieve(BluetoothController.BLUETOOTH_CONNECTED)
            sendUDSCommands()
            readMessage()
        } catch (e:IOException){
            Log.d("my","error : $e")
            listener.onRecieve(BluetoothController.BLUETOOTH_NO_CONNECTED)

        } catch (e: SecurityException){
            Log.d("my","error : $e")

        }
    }

    fun closeConnection(){
        try {
            mSocket?.close()
        } catch (e:IOException){
            Log.d("my","error : $e")

        }
    }


}
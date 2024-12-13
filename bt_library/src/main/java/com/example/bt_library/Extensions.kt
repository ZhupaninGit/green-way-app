package com.example.bt_library

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.changeImageButton(button: ImageButton,isEnabled : Boolean){
    if (isEnabled){
        button.setImageResource(R.drawable.baseline_bluetooth_24)
    } else {
        button.setImageResource(R.drawable.baseline_bluetooth_disabled_24)
    }
}

fun Fragment.checkBTPermissions():Boolean{
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    } else {
        ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
    }

}


package com.example.bt_library

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bt_library.databinding.BluetoothDevicesListBinding
import com.google.android.material.snackbar.Snackbar


class DeviceListFragment : Fragment() , DeviceAdapter.Listener {

    private var preferences : SharedPreferences? = null
    private lateinit var binding: BluetoothDevicesListBinding
    private var btAdapter : BluetoothAdapter? = null
    private lateinit var btLauncher: ActivityResultLauncher<Intent>
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var deviceAdapter : DeviceAdapter
    private lateinit var discoveryAdapter : DeviceAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BluetoothDevicesListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = activity?.getSharedPreferences("main_preference",Context.MODE_PRIVATE)
        binding.btnBluetoothSearch.setOnClickListener{
            btLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        }
        binding.btnBluetooth.setOnClickListener{
            try {
                if(btAdapter?.isEnabled == true){
                    launchPermission()
                    btAdapter?.startDiscovery()
                    binding.btnBluetooth.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE

                }
            } catch (e : SecurityException){
                Log.d("my","an error occurred ${e.toString()}")
            }
        }
        intentFilters()
        checkPermissions()
        initRcViews()
        registerBtLauncher()
        initBtAdapter()
        bluetoothState()
    }

    private fun initRcViews() = with(binding){
        rcViewPaired.layoutManager = LinearLayoutManager(requireContext())
        rcViewDiscovered.layoutManager = LinearLayoutManager(requireContext())

        deviceAdapter = DeviceAdapter(this@DeviceListFragment,false)
        discoveryAdapter = DeviceAdapter(this@DeviceListFragment,true)

        rcViewPaired.adapter = deviceAdapter
        rcViewDiscovered.adapter = discoveryAdapter
    }

    private fun getPairedDevices(){
        try{
            val list = ArrayList<ScannerDevice>()
            val deviceList = btAdapter?.bondedDevices as Set<BluetoothDevice>
            for (bluetoothDevice in deviceList) {
                list.add(
                    ScannerDevice(
                    bluetoothDevice,preferences?.getString("mac","") == bluetoothDevice.address
                    )
                )
            }
            binding.textNoPairedDevices.visibility = if(list.isEmpty()) View.VISIBLE else View.GONE
            deviceAdapter.submitList(list)
        } catch (e : SecurityException){

        }
    }


    private fun initBtAdapter(){
        val bManager = activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = bManager.adapter
    }


    private fun bluetoothState(){
        if(btAdapter?.isEnabled == true)
        {
            changeImageButton(binding.btnBluetoothSearch,true)
            getPairedDevices()
        } else changeImageButton(binding.btnBluetoothSearch,false)
    }

    private fun checkPermissions(){
        if(!checkBTPermissions()){
            registerPermissionListener()
            launchPermission()
        }
    }

    private fun launchPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            pLauncher.launch(arrayOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH
            ))
        } else {
            pLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH
                )
            )
        }
    }

    private fun registerPermissionListener(){
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
        }
    }

    private fun registerBtLauncher(){
        btLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                Snackbar.make(binding.root,"Bluetooth turned on",Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(binding.root,"Please,turn on bluetooth",Snackbar.LENGTH_LONG).show()
            }
            bluetoothState()
        }
    }

    private fun saveDevice(mac : String,name : String){
        val editor = preferences?.edit()
        editor?.putString("mac",mac)
        editor?.putString("name",name)
        editor?.apply()
    }

    override fun onClick(device: ScannerDevice) {
        saveDevice(device.device.address,device.device.name)
    }

    private val bReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
             when(intent?.action){
                 BluetoothDevice.ACTION_FOUND -> {
                     try {
                         val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                         val deviceList = mutableSetOf<ScannerDevice>()
                         deviceList.addAll(discoveryAdapter.currentList)
                         if(device != null) deviceList.add(ScannerDevice(device,false))
                         discoveryAdapter.submitList(deviceList.toList())
                         binding.textNoDiscoveredDevices.visibility = if(deviceList.isEmpty()) View.VISIBLE else View.GONE

                     } catch (e : SecurityException){
                        Log.d("connection","error : ${e}")
                     }
                 }
                 BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    getPairedDevices()
                 }
                 BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                     binding.btnBluetooth.visibility = View.VISIBLE
                     binding.progressBar.visibility = View.GONE

                 }
                 else -> {

                 }
             }
        }
    }

    private fun intentFilters(){
        val f1 = IntentFilter(BluetoothDevice.ACTION_FOUND)
        val f2 = IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        val f3 = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        activity?.registerReceiver(bReceiver,f1)
        activity?.registerReceiver(bReceiver,f2)
        activity?.registerReceiver(bReceiver,f3)
    }


}
package com.example.bt_library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bt_library.databinding.BluetoothDeviceItemBinding

class DeviceAdapter(private val listener: Listener,val adapterType : Boolean):ListAdapter<ScannerDevice, DeviceAdapter.DeviceHolder>(Comparator()) {
    private var oldCheckBox : CheckBox? = null

    class DeviceHolder(view: View,private val adapter: DeviceAdapter,private val listener : Listener,val adapterType: Boolean) : RecyclerView.ViewHolder(view){
        private val b = BluetoothDeviceItemBinding.bind(view)
        private var pairedDevices : ScannerDevice? = null



        init {
            b.setCurrentDeviceCheck.setOnClickListener {
                pairedDevices?.let { it1 -> listener.onClick(it1) }
                adapter.selectCheckBox(it as CheckBox)
            }
            itemView.setOnClickListener {
                if(adapterType){
                    pairedDevices?.device?.createBond()
                } else {
                    pairedDevices?.let { it1 -> listener.onClick(it1) }
                    adapter.selectCheckBox(b.setCurrentDeviceCheck)
                }
            }
        }
        fun bind(item: ScannerDevice) = with(b){
            b.setCurrentDeviceCheck.visibility = if(adapterType) View.GONE else View.VISIBLE
            pairedDevices = item
            deviceName.text = item.device.name
            deviceAddress.text = item.device.address
            if (item.isChecked) adapter.selectCheckBox(b.setCurrentDeviceCheck)
        }
    }

    class Comparator : DiffUtil.ItemCallback<ScannerDevice>(){
        override fun areItemsTheSame(oldItem: ScannerDevice, newItem: ScannerDevice): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ScannerDevice, newItem: ScannerDevice): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bluetooth_device_item,parent,false)
        return DeviceHolder(view,this,listener,adapterType)
    }

    override fun onBindViewHolder(holder: DeviceHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun selectCheckBox(checkBox: CheckBox){
        oldCheckBox?.isChecked = false
        oldCheckBox = checkBox
        oldCheckBox?.isChecked = true
    }

    interface Listener{
        fun onClick(device: ScannerDevice)
    }
}
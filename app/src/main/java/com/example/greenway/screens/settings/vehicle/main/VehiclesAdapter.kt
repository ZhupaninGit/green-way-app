package com.example.greenway.screens.settings.vehicle.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.greenway.R
import com.example.greenway.models.FuelType
import com.example.greenway.models.TransmissionType
import com.example.greenway.models.Vehicle
import com.example.greenway.utils.showToast

class VehiclesAdapter(private val viewModel : MainFragmentViewModel) : RecyclerView.Adapter<VehiclesAdapter.MainHolder>() {
    private var currentActiveCheckBox : CheckBox? = null
    private var listVehicles = emptyList<Vehicle>()


    class MainHolder(view: View): RecyclerView.ViewHolder(view){
        var vehicleName : TextView = view.findViewById(R.id.vehicle_name)
        var vehicleDetails : TextView = view.findViewById(R.id.vehicle_detail)
        var activeVehicle : CheckBox = view.findViewById(R.id.active_vehicle)
        val deleteVehicleButton : ImageButton = view.findViewById(R.id.btn_delete_veh)
    }

    override fun onViewAttachedToWindow(holder: MainHolder) {
        holder.activeVehicle.setOnClickListener {
            val vehicle = listVehicles[holder.adapterPosition]
            if (!vehicle.isActive){
                MainFragment.clickToCheckBox(vehicle, viewModel)
                setCheckBox(it as CheckBox)
            }
            else {
                setCheckBox(it as CheckBox)
                showToast("You can`t uncheck it,because some vehicle must be active.")
            }
        }

        holder.deleteVehicleButton.setOnClickListener {
            val vehicle = listVehicles[holder.adapterPosition]
            if (!vehicle.isActive){
                MainFragment.delete(vehicle, viewModel)
            }
            else {
                showToast("You can`t delete active vehicle.")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_item,parent,false)
        return MainHolder(view)
    }

    override fun getItemCount(): Int {
       return listVehicles.size
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val fuelType : String = if(listVehicles[position].fuelType == FuelType.DIESEL) "Diesel" else "Gasoline"
        val transmissionType : String = if(listVehicles[position].transmissionType == TransmissionType.AUTO) "Auto" else "Manual"

        holder.vehicleName.text = listVehicles[position].name
        holder.vehicleDetails.text = "$fuelType , $transmissionType"
        if (listVehicles[position].isActive){
            holder.activeVehicle.isChecked = true
            currentActiveCheckBox = holder.activeVehicle
        }

    }

    fun setCheckBox(checkBox: CheckBox){
        currentActiveCheckBox?.isChecked = false
        currentActiveCheckBox = checkBox
        currentActiveCheckBox?.isChecked = true

    }

    fun setList(list: List<Vehicle>){
        listVehicles = list
        notifyDataSetChanged()
    }
}
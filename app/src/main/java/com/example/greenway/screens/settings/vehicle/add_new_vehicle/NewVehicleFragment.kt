package com.example.greenway.screens.settings.vehicle.add_new_vehicle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.greenway.R
import com.example.greenway.databinding.FragmentNewVehicleBinding
import com.example.greenway.models.FuelType
import com.example.greenway.models.TransmissionType
import com.example.greenway.models.Vehicle
import com.example.greenway.utils.APP_ACTIVITY
import com.example.greenway.utils.showToast


class NewVehicleFragment : Fragment() {

    private var _binding: FragmentNewVehicleBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var viewModel: NewVehicleViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewVehicleBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization(){
        viewModel = ViewModelProvider(this).get(NewVehicleViewModel::class.java)
        mBinding.btnAddCreatedVehicle.setOnClickListener {
            try {
                val vehicleName = mBinding.inputVehicleName.editText?.text.toString()
                val maxRPM = mBinding.inputMaxRpmName.editText?.text.toString().toInt()

                val selectedFuelTypeId = mBinding.radioFuelType.checkedRadioButtonId
                val fuelTypeString = requireView().findViewById<RadioButton>(selectedFuelTypeId).text.toString()
                val fuelType : FuelType = if(fuelTypeString == "Diesel") FuelType.DIESEL else FuelType.GASOLINE

                val selectedTransmissionTypeId = mBinding.radioTransmissionType.checkedRadioButtonId
                val transmissionTypeString = requireView().findViewById<RadioButton>(selectedTransmissionTypeId).text.toString()
                val transmissionType : TransmissionType = if(transmissionTypeString == "Manual") TransmissionType.MANUAL else TransmissionType.AUTO


                if (vehicleName.isEmpty()){
                    showToast("Vehicle name cannot be empty.")
                } else {
                    try {
                        viewModel.insert(Vehicle(
                            name = vehicleName,maxRPM = maxRPM,fuelType = fuelType,transmissionType = transmissionType)){
                            APP_ACTIVITY.navController.navigate(R.id.action_newVehicleFragment_to_startingFragment)
                        }
                    } catch (e : Exception){
                        Log.d("my",e.toString())
                    }
                }

            } catch (e : NumberFormatException){
                showToast("Please,provide integer number to Max RPM field.")
            } catch (e : Exception){
                showToast(e.toString())
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
package com.example.greenway.screens.settings.vehicle.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.greenway.R
import com.example.greenway.databinding.FragmentStartingBinding
import com.example.greenway.models.Vehicle
import com.example.greenway.utils.APP_ACTIVITY


class MainFragment : Fragment() {

    private var _binding : FragmentStartingBinding? = null
    private  val mBinding get() = _binding!!

    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VehiclesAdapter
    private lateinit var observer: Observer<List<Vehicle>>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartingBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialize()

    }

    private fun initialize(){
        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        adapter = VehiclesAdapter(viewModel)
        recyclerView = mBinding.recyclerView
        recyclerView.adapter = adapter

        observer = Observer{
            val list = it
            adapter.setList(list)
        }
        viewModel.allVehicles.observe(this,observer)
        mBinding.btnAddVehicle.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_startingFragment_to_newVehicleFragment)
        }
    }

    private fun onSuccess(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.allVehicles.removeObserver(observer)
        recyclerView.adapter = null
    }

    companion object{
        fun clickToCheckBox(newActiveVehicle: Vehicle, viewModel: MainFragmentViewModel) {
            println(newActiveVehicle.name + newActiveVehicle.isActive)
            newActiveVehicle.isActive = !newActiveVehicle.isActive
            viewModel.updateActiveVehicle(newActiveVehicle) {
            }
        }
        fun delete(vehicle: Vehicle, viewModel: MainFragmentViewModel) {
            viewModel.deleteVehicle(vehicle) {
            }
        }
    }


}
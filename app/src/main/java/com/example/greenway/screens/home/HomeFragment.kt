package com.example.greenway.screens.home

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bt_library.BluetoothController
import com.example.greenway.databinding.FragmentHomeBinding
import com.example.greenway.utils.showToast


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var btController: BluetoothController
    private lateinit var btAdapter: BluetoothAdapter
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        observeConnectionState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getdata.setOnClickListener {
            println("We are in viewCreated")
            viewModel.sendData(binding.inputText.text.toString())
        }
    }


    private fun observeConnectionState() {
        viewModel.connectionState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeFragmentViewModel.BluetoothState.CONNECTED -> showToast("Connected")
                HomeFragmentViewModel.BluetoothState.CONNECTING -> showToast("Connecting")
                HomeFragmentViewModel.BluetoothState.NOT_CONNECTED -> showToast("Error when connecting")
            }
        }


    }

    fun connect(){
        viewModel.connect()
    }

}
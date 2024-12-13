package com.example.greenway.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.greenway.R
import com.example.greenway.databinding.FragmentSettingsBinding
import com.example.greenway.utils.APP_ACTIVITY


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : SettingsFragmentViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    private fun initialize(){
        viewModel = ViewModelProvider(this).get(SettingsFragmentViewModel::class.java)

        viewModel.activeVehicle.observe(viewLifecycleOwner) { vehicle ->
            if (vehicle != null) {
                binding.vehNameSettings.text = vehicle.name
            }
        }

        binding.scannerNameSettings.text = viewModel.getActiveScannerName()

        binding.cardViewDevice.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_settings_to_deviceListFragment)
        }
        binding.cardViewPreferences.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_settings_to_startingFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
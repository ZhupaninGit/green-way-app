//package com.example.greenway
//
//import android.bluetooth.BluetoothAdapter
//import android.bluetooth.BluetoothManager
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.content.res.AppCompatResources
//import androidx.fragment.app.Fragment
//import com.example.bt_library.BluetoothController
//import com.example.greenway.databinding.FragmentFirstBinding
//
//
//class MainFragment : Fragment(),BluetoothController.Listener {
//
//    private lateinit var binding: FragmentFirstBinding
//    private lateinit var btController: BluetoothController
//    private lateinit var btAdapter: BluetoothAdapter
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentFirstBinding.inflate(inflater, container, false)
//        return binding.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initBtAdapter()
//        val preferences = activity?.getSharedPreferences("main_preference",Context.MODE_PRIVATE)
//        val mac = preferences?.getString("mac","")
//        btController = BluetoothController(btAdapter)
//
//
//        binding.btnConnect.setOnClickListener{
//            btController.connect(mac ?: "",this)
//        }
//        binding.getData.setOnClickListener {
//            btController.getData()
//        }
//    }
//    private fun initBtAdapter(){
//        val bManager = activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//        btAdapter = bManager.adapter
//    }
//
//    override fun onRecieve(msg: String) {
//        activity?.runOnUiThread {
//            when(msg){
//                BluetoothController.BLUETOOTH_CONNECTED ->{
//                    binding.btnConnect.backgroundTintList = AppCompatResources.getColorStateList(requireContext(),R.color.black)
//                    binding.btnConnect.text = "You are connected"
//                }
//                BluetoothController.BLUETOOTH_NO_CONNECTED ->{
//                    binding.btnConnect.backgroundTintList = AppCompatResources.getColorStateList(requireContext(),R.color.white)
//                    binding.btnConnect.text = "Connect"
//                }
//                else -> {
//                    binding.resulttext.text = msg
//                }
//            }
//        }
//    }
//
//}
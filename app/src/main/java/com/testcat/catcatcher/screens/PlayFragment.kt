package com.testcat.catcatcher.screens

import android.content.Context
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.testcat.catcatcher.models.PlayViewModel
import com.testcat.catcatcher.databinding.FragmentPlayBinding

class PlayFragment : Fragment() {


    private lateinit var viewModel: PlayViewModel

    private var _binding: FragmentPlayBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySensorEventListener : MySensorEventListener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlayViewModel::class.java)
        val sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mySensorEventListener = MySensorEventListener(binding)
        sensorManager.registerListener(
            mySensorEventListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        val screenSize = getScreenSize(requireActivity())
        val screenWidth = screenSize.first
        val screenHeight = screenSize.second

        println("sizeeee")
        println(screenWidth)
        println(screenHeight)



        binding.bed.y = (screenHeight-350).toFloat()
        binding.bed.x = (screenWidth/2).toFloat()
    }

    private fun getScreenSize(context: Context): Pair<Int, Int> {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val screenWidth = size.x
        val screenHeight = size.y
        return Pair(screenWidth, screenHeight)
    }

}

class MySensorEventListener(val binding: FragmentPlayBinding) : SensorEventListener {

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        binding.bed.x -= x

        println(binding.bed.x)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }
}
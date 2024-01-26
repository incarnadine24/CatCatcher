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
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.testcat.catcatcher.ChangeList
import com.testcat.catcatcher.MainActivity
import com.testcat.catcatcher.R
import com.testcat.catcatcher.models.PlayViewModel
import com.testcat.catcatcher.databinding.FragmentPlayBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayFragment : Fragment() {

    private lateinit var viewModel: PlayViewModel
    private var _binding: FragmentPlayBinding? = null
    private val binding get() = _binding!!
    var endGame = "true"
    private lateinit var mySensorEventListener: MySensorEventListener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlayViewModel::class.java)
        binding.pause.setOnClickListener {
            viewModel.changers.value = "false"
            binding.pauseFrame.visibility = View.VISIBLE
        }
        binding.continueButton.setOnClickListener {
            binding.pauseFrame.visibility = View.INVISIBLE
            viewModel.changers.value = "true"
        }

        val sensorManager =
            requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        mySensorEventListener = MySensorEventListener(binding, viewModel)
        sensorManager.registerListener(
            mySensorEventListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        val catsneko = mutableListOf(
            R.drawable.neko1,
            R.drawable.neko2,
            R.drawable.neko3,
            R.drawable.neko4,
            R.drawable.neko5,
            R.drawable.neko6
        )
        catsneko.shuffle()
        binding.cat.setImageResource(catsneko[0])
        binding.score.text = "Score : ${viewModel.count.value}"
        val screenSize = getScreenSize(requireActivity())
        val screenWidth = screenSize.first
        val screenHeight = screenSize.second
        binding.bed.y = (screenHeight - 350).toFloat()
        binding.bed.x = (screenWidth / 2).toFloat()
        viewModel.bedxvalue.value = binding.bed.x
        viewModel.bedyvalue.value = binding.bed.y

        val cat = binding.cat

        cat.x = ((0..screenWidth - 150).random()).toFloat()
        viewModel.catxvalue.value = cat.x
        viewModel.catyvalue.value = 0f
        cat.y = viewModel.catyvalue.value!!

        binding.restartButton.setOnClickListener {
            binding.pauseFrame.visibility = View.INVISIBLE
            viewModel.count.value = 0
            viewModel.changers.value = "true"
            cat.x = ((0..screenWidth - 150).random()).toFloat()
            viewModel.catxvalue.value = cat.x
            viewModel.catyvalue.value = 0f
            cat.y = viewModel.catyvalue.value!!
            catsneko.shuffle()
            binding.cat.setImageResource(catsneko[0])
            binding.score.text = "Score : ${viewModel.count.value}"
        }
        viewModel.changers.observe(requireActivity(), Observer {
            if (it == "true") {
                viewModel.pause.value = "false"
                endGame = "false"
                GlobalScope.launch(Dispatchers.Main) {
                    while (viewModel.pause.value == "false" && endGame == "false") {
                        viewModel.catyvalue.value =
                            viewModel.catyvalue.value!!.plus(viewModel.speed.value!! + viewModel.count.value!! * 2)
                        delay(100)
                    }
                }
            } else {
                viewModel.pause.value="true"

            }
        })

        viewModel.catyvalue.observe(requireActivity(), Observer {
            cat.y = it
            if (it > (screenHeight - 350).toFloat() && (viewModel.catxvalue.value!! - viewModel.bedxvalue.value!!) in (0f..200f)) {

                viewModel.count.value = viewModel.count.value?.plus(1)

                viewModel.catxvalue.value = ((0..screenWidth - 150).random()).toFloat()
                viewModel.catyvalue.value = 0f

                cat.x = viewModel.catxvalue.value!!
                cat.y = viewModel.catyvalue.value!!
                catsneko.shuffle()
                binding.cat.setImageResource(catsneko[0])

            } else if (it > (screenHeight - 150).toFloat()) {
                endGame = "true"
                ChangeList(activity as MainActivity).changingList(viewModel.count.value!!)
                viewModel.count.value = 0
                findNavController().navigate(R.id.action_playFragment_to_finishFragment)

            }
        }
        )

        viewModel.count.observe(requireActivity(), Observer {
            binding.score.text = "Score : $it"

        })

        binding.bed.y = (screenHeight - 350).toFloat()
        binding.bed.x = (screenWidth / 2).toFloat()
        binding.menuButton.setOnClickListener {
            ChangeList(activity as MainActivity).changingList(viewModel.count.value!!)
            viewModel.count.value = 0
            findNavController().navigate(R.id.action_playFragment_to_menuFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            ChangeList(activity as MainActivity).changingList(viewModel.count.value!!)
            viewModel.count.value = 0
            findNavController().navigate(R.id.action_playFragment_to_menuFragment)
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        endGame = "true"
        _binding = null
    }
}

class MySensorEventListener(val binding: FragmentPlayBinding, val viewModel: PlayViewModel) :
    SensorEventListener {

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        if (viewModel.pause.value == "false") {
            binding.bed.x -= x
            viewModel.bedxvalue.value = binding.bed.x
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }
}
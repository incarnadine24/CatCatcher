package com.testcat.catcatcher.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testcat.catcatcher.AppPrefs
import com.testcat.catcatcher.MainActivity
import com.testcat.catcatcher.databinding.FragmentScoresBinding

class ScoresFragment : Fragment() {

    private var _binding: FragmentScoresBinding? = null
    private val binding get() = _binding!!
    private var mutableList = mutableListOf<Int>(0, 0, 0, 0, 0)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoresBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appPrefs = AppPrefs(activity as MainActivity)
        mutableList = appPrefs.getList("scoreKey").toMutableList()

        val listOfScoresBinding = listOf(
            binding.first,
            binding.second,
            binding.third,
            binding.fourth,
            binding.fifth
        )

        val listOfNumbers = listOf("1.","2.","3.","4.","5.")
        for(i in listOfScoresBinding.indices){

            listOfScoresBinding[i].text = "${listOfNumbers[i]} ${mutableList[i]}"

        }
    }
}
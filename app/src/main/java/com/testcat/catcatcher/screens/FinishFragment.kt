package com.testcat.catcatcher.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.testcat.catcatcher.AppPrefs
import com.testcat.catcatcher.MainActivity
import com.testcat.catcatcher.R
import com.testcat.catcatcher.databinding.FragmentFinishBinding

class FinishFragment : Fragment() {


    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        println("ghujyhftgdrfthyyhftgrdfsegjy")
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("k775yhrhjtjtrhdrtyhbgrt")
        val appPrefs : AppPrefs = AppPrefs(activity as MainActivity)
        binding.scoreText.text = "Score: ${appPrefs.getList("scoreKey")[0]}"
        binding.menuButton.setOnClickListener {
            findNavController().navigate(R.id.action_finishFragment_to_menuFragment)
        }
        binding.restartButton.setOnClickListener {
            findNavController().navigate(R.id.action_finishFragment_to_playFragment)
        }
    }

}
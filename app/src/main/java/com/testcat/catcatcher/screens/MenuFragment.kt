package com.testcat.catcatcher.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.testcat.catcatcher.AppPrefs
import com.testcat.catcatcher.MainActivity
import com.testcat.catcatcher.R
import com.testcat.catcatcher.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getListFromPrefs = AppPrefs(activity as MainActivity).getList("scoreKey")
        if(getListFromPrefs.isEmpty()){
            AppPrefs(activity as MainActivity).saveList("scoreKey",listOf(0,0,0,0,0))
        }
        binding.playButton.setOnClickListener{
            findNavController().navigate(R.id.action_menuFragment_to_playFragment)
        }
        binding.scoresButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_scoresFragment)
        }
        binding.rateButton.setOnClickListener {
            rateGame()
        }

        binding.shareButton.setOnClickListener {
            sharedGame()
        }

        binding.quitButton.setOnClickListener {
            (activity as MainActivity).finish()
        }
    }

    private fun sharedGame(){
        val intentShared = Intent(Intent.ACTION_SEND)
        intentShared.apply{
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.testcat.catcatcher")
        }
        startActivity(intentShared)
    }

    private fun rateGame(){
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("market://details?id=com.testcat.catcatcher")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivityForResult(intent, 22)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
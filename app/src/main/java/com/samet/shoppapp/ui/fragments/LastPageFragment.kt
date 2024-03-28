package com.samet.shoppapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.samet.shoppapp.R
import com.samet.shoppapp.databinding.FragmentLastPageBinding


class LastPageFragment : Fragment() {
    private lateinit var binding: FragmentLastPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLastPageBinding.inflate(inflater, container, false)

        binding.buttonOk.setOnClickListener {
            val action = LastPageFragmentDirections.actionLastPageFragmentToProductsFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

}
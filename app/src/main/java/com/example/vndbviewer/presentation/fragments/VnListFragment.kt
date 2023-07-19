package com.example.vndbviewer.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vndbviewer.databinding.FragmentVnListBinding
import com.example.vndbviewer.presentation.adapters.VnListAdapter
import com.example.vndbviewer.presentation.viewmodels.VnListViewModel

class VnListFragment : Fragment() {

    private var _binding: FragmentVnListBinding? = null
    private val binding: FragmentVnListBinding
        get() = _binding ?: throw RuntimeException("VnListFragment == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[VnListViewModel::class.java]
    }

    private val adapter by lazy {
        VnListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVnListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        adapter.onVnClickListener = {
            findNavController().navigate(VnListFragmentDirections.actionVnListFragmentToVnDetailsFragment(it.id))
            Log.d("CLICK", "Clicked on ${it.id}")
        }
        binding.vnList.adapter = adapter
        viewModel.vnList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
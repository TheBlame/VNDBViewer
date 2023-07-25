package com.example.vndbviewer.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.vndbviewer.R
import com.example.vndbviewer.databinding.FragmentVnDetailsBinding
import com.example.vndbviewer.presentation.viewmodels.VnItemViewModel

class VnDetailsFragment : Fragment() {

    private val args by navArgs<VnDetailsFragmentArgs>()

    private var _binding: FragmentVnDetailsBinding? = null
    private val binding: FragmentVnDetailsBinding
        get() = _binding ?: throw RuntimeException("VnDetails == null")

    val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VnItemViewModel(activity!!.application, args.id) as T
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(this, factory).get(VnItemViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVnDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.vnDetails.observe(viewLifecycleOwner, Observer {
            binding.poster.load(it.image?.url) {
                crossfade(true)
                crossfade(200)
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
            binding.rating.text = it.rating.toString()
            binding.tittle.text = it.title
            binding.description.text = it.description
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
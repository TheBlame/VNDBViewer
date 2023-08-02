package com.example.vndbviewer.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.vndbviewer.R
import com.example.vndbviewer.databinding.FragmentVnDetailsBinding
import com.example.vndbviewer.presentation.VndbApplication
import com.example.vndbviewer.presentation.viewmodels.ViewModelFactory
import com.example.vndbviewer.presentation.viewmodels.VnItemViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class VnDetailsFragment : Fragment() {

    private val args by navArgs<VnDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: FragmentVnDetailsBinding? = null
    private val binding: FragmentVnDetailsBinding
        get() = _binding ?: throw RuntimeException("VnDetails == null")


    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[VnItemViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as VndbApplication).component
            .fragmentComponentFactory()
            .create(args.id)
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.vnDetails.collect {
                    binding.poster.load(it.image) {
                        crossfade(true)
                        crossfade(200)
                        placeholder(R.drawable.loading_animation)
                        error(R.drawable.ic_broken_image)
                    }
                    binding.rating.text = it.rating.toString()
                    binding.tittle.text = it.title
                    binding.description.text = it.description
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
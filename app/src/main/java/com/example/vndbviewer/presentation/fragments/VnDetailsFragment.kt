package com.example.vndbviewer.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
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
import com.example.vndbviewer.presentation.adapters.TagListAdapter
import com.example.vndbviewer.presentation.viewmodels.ViewModelFactory
import com.example.vndbviewer.presentation.viewmodels.VnItemViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collectLatest
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

    private val tagListAdapter by lazy {
        TagListAdapter()
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
        binding.tagList.adapter = tagListAdapter
        setupChips()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collectLatest { state ->
                    binding.poster.load(state.vn.image) {
                        crossfade(true)
                        crossfade(200)
                        placeholder(R.drawable.loading_animation)
                        error(R.drawable.ic_broken_image)
                    }
                    binding.rating.text = state.vn.rating.toString()
                    binding.tittle.text = state.vn.title
                    binding.description.text = state.vn.description
                    tagListAdapter.submitList(state.vn.tags)
                    binding.tagContentChip.isChecked = state.content
                    binding.tagSexualContentChip.isChecked = state.sexual
                    binding.tagTechnicalChip.isChecked = state.technical
                }
            }
        }
    }

    private fun setupChips() {
        binding.tagSexualContentChip.setOnClickListener {
            viewModel.changeSexualContent()
        }
        binding.tagContentChip.setOnClickListener {
            viewModel.changeContent()
        }
        binding.tagTechnicalChip.setOnClickListener {
            viewModel.changeTechnical()
        }
        binding.spoilerLvlGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            when (group.checkedChipId) {
                binding.spoilerLvl0.id -> viewModel.changeSpoilerLvlTo0()
                binding.spoilerLvl1.id -> viewModel.changeSpoilerLvlTo1()
                binding.spoilerLvl2.id -> viewModel.changeSpoilerLvlTo2()
            }
        }

        binding.spoilerQuantityGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            when(group.checkedChipId) {
                binding.spoilerSummary.id -> viewModel.changeSpoilerQuantityToSummary()
                binding.spoilerAll.id -> viewModel.changeSpoilerQuantityToAll()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
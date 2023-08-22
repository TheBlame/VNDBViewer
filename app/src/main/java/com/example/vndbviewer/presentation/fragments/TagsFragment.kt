package com.example.vndbviewer.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.vndbviewer.databinding.FragmentTagsBinding
import com.example.vndbviewer.presentation.adapters.TagListAdapter
import com.example.vndbviewer.presentation.viewmodels.VnItemViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class TagsFragment : Fragment() {

    private var _binding: FragmentTagsBinding? = null
    private val binding: FragmentTagsBinding
        get() = _binding ?: throw RuntimeException("TagsFragment == null")

    private val tagListAdapter by lazy {
        TagListAdapter()
    }

    private val viewModel: VnItemViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTagsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tagList.adapter = tagListAdapter
        setupChips()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collectLatest { state ->
                    tagListAdapter.submitList(state.vn.tags)
                    binding.tagContentChip.isChecked = state.content
                    binding.tagSexualContentChip.isChecked = state.sexual
                    binding.tagTechnicalChip.isChecked = state.technical
                    //      TODO() проверка стейтов всех чип групп
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
            when (group.checkedChipId) {
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
package com.example.vndbviewer.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.vndbviewer.data.network.pojo.Tags
import com.example.vndbviewer.databinding.FragmentVnDetailsBinding
import com.example.vndbviewer.domain.Vn
import com.example.vndbviewer.presentation.VndbApplication
import com.example.vndbviewer.presentation.adapters.TagListAdapter
import com.example.vndbviewer.presentation.viewmodels.ViewModelFactory
import com.example.vndbviewer.presentation.viewmodels.VnItemViewModel
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

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.vnDetails.collectLatest {state ->
                    if (state != null) {
                        binding.poster.load(state.vn.image) {
                            crossfade(true)
                            crossfade(200)
                            placeholder(R.drawable.loading_animation)
                            error(R.drawable.ic_broken_image)
                        }
                        binding.rating.text = state.vn.rating.toString()
                        binding.tittle.text = state.vn.title
                        binding.description.text = state.vn.description
                        filter(state)
                        Log.d("vmcollect", state.toString())
                        setupButtons(state)
                    }
                }
            }
        }
    }

    private fun setupButtons(state: VnItemViewModel.State) {
        binding.tagSexualContentButton.setOnClickListener {
            viewModel.changeSexualContent()
            filter(state)
        }
        binding.tagContentButton.setOnClickListener {
            viewModel.changeContent()
            filter(state)
        }
        binding.tagTechnicalButton.setOnClickListener {
            viewModel.changeTechnical()
            filter(state)
        }
    }


    private fun filter(state: VnItemViewModel.State) {
        val result = state.vn.tags?.toMutableList()
        for (i in state.vn.tags.orEmpty()) {
            if (!state.sexual && i.category == "ero") {
                result?.remove(i)
            }
            if (!state.content && i.category == "cont") {
                result?.remove(i)
            }
            if (!state.technical && i.category == "tech") {
                result?.remove(i)
            }
        }
        tagListAdapter.submitList(result)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
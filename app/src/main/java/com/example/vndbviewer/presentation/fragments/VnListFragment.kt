package com.example.vndbviewer.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.vndbviewer.databinding.FragmentVnListBinding
import com.example.vndbviewer.presentation.VndbApplication
import com.example.vndbviewer.presentation.adapters.VnListAdapter
import com.example.vndbviewer.presentation.adapters.VnLoadStateAdapter
import com.example.vndbviewer.presentation.viewmodels.Factory
import com.example.vndbviewer.presentation.viewmodels.VnListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class VnListFragment : Fragment() {

    private var _binding: FragmentVnListBinding? = null
    private val binding: FragmentVnListBinding
        get() = _binding ?: throw RuntimeException("VnListFragment == null")

    private val component by lazy {
        (requireActivity().application as VndbApplication).component
    }

    private val viewModel by lazyViewModel {
        stateHandle ->  component.vnListViewModel().create(stateHandle)
    }


    private val vnListAdapter by lazy {
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
        vnListAdapter.onVnClickListener = {
            findNavController().navigate(
                VnListFragmentDirections.actionVnListFragmentToVnDetailsFragment(
                    it.id
                )
            )
            Log.d("CLICK", "Clicked on ${it.id}")
        }
        binding.retryButton.setOnClickListener { vnListAdapter.retry() }
        val header = VnLoadStateAdapter { vnListAdapter.retry() }
        binding.vnList.adapter = vnListAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = VnLoadStateAdapter { vnListAdapter.retry() }
        )
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {

                viewModel.vnList.collectLatest {
                    Log.d("paging", "$it.is")
                    vnListAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                vnListAdapter.loadStateFlow.collect { loadState ->
                    // Show a retry header if there was an error refreshing, and items were previously
                    // cached OR default to the default prepend state
                    header.loadState = loadState.mediator
                        ?.refresh
                        ?.takeIf { it is LoadState.Error && vnListAdapter.itemCount > 0 }
                        ?: loadState.prepend

                    val isListEmpty =
                        loadState.refresh is LoadState.NotLoading && vnListAdapter.itemCount == 0
                    // show empty list
                    binding.emptyList.isVisible = isListEmpty
                    // Only show the list if refresh succeeds, either from the the local db or the remote.
                    binding.vnList.isVisible =
                        loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                    // Show loading spinner during initial load or refresh.
                    binding.progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
                    // Show the retry state if initial load or refresh fails.
                    binding.retryButton.isVisible =
                        loadState.mediator?.refresh is LoadState.Error && vnListAdapter.itemCount == 0

                    // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error
                    errorState?.let {
                    }
                }
            }

        }
    }

    inline fun <reified T : ViewModel> Fragment.lazyViewModel(
        noinline create: (stateHandle: SavedStateHandle) -> T
    ) = viewModels<T> {
        Factory(this, create)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
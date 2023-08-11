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
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.vndbviewer.R
import com.example.vndbviewer.databinding.FragmentVnDetailsBinding
import com.example.vndbviewer.presentation.VndbApplication
import com.example.vndbviewer.presentation.adapters.ViewPagerAdapter
import com.example.vndbviewer.presentation.viewmodels.ViewModelFactory
import com.example.vndbviewer.presentation.viewmodels.VnItemViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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

    private val fragList by lazy { listOf(
        TagsFragment(viewModel),
        Todo1Fragment(),
        Todo2Fragment()
    ) }

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

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
        tabLayout = binding.tabs
        viewPager = binding.viewPager
        viewPager.adapter = activity?.let { ViewPagerAdapter(it, fragList) }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "Tags"
                1 -> "TODO1"
                2 -> "TODO2"
                else -> throw Exception()
            }
        }.attach()

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
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
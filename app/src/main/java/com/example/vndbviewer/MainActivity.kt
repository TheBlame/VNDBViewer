package com.example.vndbviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vndbviewer.adapters.VnListAdapter
import com.example.vndbviewer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: VnViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = VnListAdapter(this)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vnList.adapter = adapter
        viewModel = ViewModelProvider(this)[VnViewModel::class.java]
        viewModel.vnList.observe(this, Observer { adapter.vnList = it })

    }
}
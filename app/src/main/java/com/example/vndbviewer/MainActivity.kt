package com.example.vndbviewer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vndbviewer.adapters.VnListAdapter
import com.example.vndbviewer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: VnListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        val adapter = VnListAdapter(this)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter.onVnClickListener = {
            val intent = VnDetailsActivity.newIntent(this@MainActivity, it.id)
            Log.d("CLICK", "Clicked on ${it.id}")
            startActivity(intent)
        }

        binding.vnList.adapter = adapter
        viewModel = ViewModelProvider(this)[VnListViewModel::class.java]
        viewModel.vnList.observe(this, Observer { adapter.submitList(it) })

    }
}
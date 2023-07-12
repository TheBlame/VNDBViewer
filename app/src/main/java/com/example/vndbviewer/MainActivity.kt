package com.example.vndbviewer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.example.vndbviewer.adapters.VnListAdapter
import com.example.vndbviewer.databinding.ActivityMainBinding
import com.example.vndbviewer.network.pojo.VnList

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: VnViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = VnListAdapter(this)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter.onVnClickListener = object : VnListAdapter.OnVnClickListener {
            override fun onVnClick(vnList: VnList) {
                val intent = VnDetailsActivity.newIntent(
                    this@MainActivity,
                    vnList.id
                )
                Log.d("CLICK", "Clicked on ${vnList.id}")
                startActivity(intent)
            }
        }
        binding.vnList.adapter = adapter
        viewModel = ViewModelProvider(this)[VnViewModel::class.java]
        viewModel.vnList.observe(this, Observer { adapter.vnList = it })

    }
}
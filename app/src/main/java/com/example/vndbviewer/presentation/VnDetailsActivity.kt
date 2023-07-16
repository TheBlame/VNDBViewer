package com.example.vndbviewer.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.vndbviewer.R
import com.example.vndbviewer.databinding.ActivityVnDetailsBinding
import com.example.vndbviewer.presentation.viewmodels.VnItemViewModel

class VnDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: VnItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVnDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!intent.hasExtra(ID)) {
            finish()
            return
        }
        val id = intent.getStringExtra(ID).toString()
        viewModel = ViewModelProvider(this)[VnItemViewModel::class.java]
        viewModel.loadCertainVnInfo(id)
        viewModel.vnDetails.observe(this, Observer {
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

    companion object {
        private const val ID = "id"
        fun newIntent(context: Context, id: String): Intent {
            val intent = Intent(context, VnDetailsActivity::class.java)
            intent.putExtra(ID, id)
            return intent
        }
    }
}
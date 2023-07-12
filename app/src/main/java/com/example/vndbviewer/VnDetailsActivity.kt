package com.example.vndbviewer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.vndbviewer.databinding.ActivityVnDetailsBinding

class VnDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: VnViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVnDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!intent.hasExtra(ID)) {
            finish()
            return
        }
        val id = intent.getStringExtra(ID).toString()
        viewModel = ViewModelProvider(this)[VnViewModel::class.java]
        viewModel.getVnDetails(id).observe(this, Observer {
            binding.poster.load(it.image?.url) {
                crossfade(true)
                crossfade(1000)
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
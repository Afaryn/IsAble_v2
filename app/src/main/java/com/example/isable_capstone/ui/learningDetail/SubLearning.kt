package com.example.isable_capstone.ui.learningDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.isable_capstone.R
import com.example.isable_capstone.databinding.ActivitySubLearningBinding
import com.example.isable_capstone.model.ResultState
import com.example.isable_capstone.ui.ViewModelFactory

class SubLearning : AppCompatActivity() {

    private val viewModel by viewModels<SubLearningViewModel>{
        ViewModelFactory.getInstance(this)
    }
    private lateinit var adapter: SubLearningAdapter
    private lateinit var binding:ActivitySubLearningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySubLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val modul = intent.getStringExtra(EXTRA_KEY)
        val bundle =Bundle()
        bundle.putString(EXTRA_KEY,modul)
        Log.d("MODULLLLLLL", "MODUL YG TERPILIH: ${modul}")

        // Inisialisasi RecyclerView dan Adapter
        val layoutManager = LinearLayoutManager(this)
        binding.rv.layoutManager = layoutManager

        adapter = SubLearningAdapter()

        viewModel.getAll(modul!!).observe(this){modul->
            when(modul){
                is ResultState.Loading->{
                    showLoading(true)
                }
                is ResultState.Success->{
                    val datamodul = modul.data
                    adapter.submitList(datamodul)
                    binding.rv.adapter=adapter
                    showLoading(false)
                }
                else -> {}
            }
        }
//        adapter.setOnItemClickCallback { story ->
//            val intent = Intent(this@SubLearning, LearningDetailActivity::class.java)
////            intent.putExtra(DetailActivity.EXTRA_TOKEN,user.token)
////            intent.putExtra(DetailActivity.EXTRA_ID_STORY,story.id)
//            startActivity(intent)
//        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val EXTRA_KEY="extra_key"
    }

}
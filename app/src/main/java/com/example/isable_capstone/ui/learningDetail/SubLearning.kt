package com.example.isable_capstone.ui.learningDetail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isable_capstone.databinding.ActivitySubLearningBinding
import com.example.isable_capstone.model.ResultState
import com.example.isable_capstone.ui.ViewModelFactory


class SubLearning : AppCompatActivity() {

    private val viewModel by viewModels<SubLearningViewModel>{
        ViewModelFactory.getInstance(this)
    }
    private lateinit var adapter: SubLearningAdapter
    private lateinit var binding: ActivitySubLearningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySubLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rv.layoutManager = layoutManager
        adapter = SubLearningAdapter()

        val modul = intent.getStringExtra(EXTRA_KEY)
        val bundle =Bundle()
        bundle.putString(EXTRA_KEY,modul)

        // Inisialisasi RecyclerView dan Adapter


        viewModel.getAll(modul!!).observe(this){materi->
            when(materi){
                is ResultState.Loading->{
                    showLoading(true)
                }
                is ResultState.Success->{
                    val datamodul = materi.data
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
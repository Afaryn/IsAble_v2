package com.example.isable_capstone.ui.learningDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isable_capstone.api.ApiConfig
import com.example.isable_capstone.api.response.LearningResponseItem
import com.example.isable_capstone.databinding.ActivitySubLearningBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class SubLearning : AppCompatActivity() {

    private lateinit var learning: LearnAdapter
    private lateinit var binding:ActivitySubLearningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySubLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val path = intent.getStringExtra("path")

        binding.rv.layoutManager = LinearLayoutManager(this) // Set a LinearLayoutManager
        learning = LearnAdapter(this, arrayListOf())
        binding.rv.setHasFixedSize(false)
        binding.rv.adapter = learning

        binding.imgToolbarBtnBack.setOnClickListener{onBackPressed()}
        if (path=="abjad"){
            binding.tvJudulKelas.text="Alphabet"
        }else if(path=="angka"){
            binding.tvJudulKelas.text="Number"
        }

        getLearning(path.toString())
    }
    private fun getLearning(path : String) {
        showLoading(true)
        ApiConfig.getApiService().getAll(path).enqueue(object :
            Callback<ArrayList<LearningResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<LearningResponseItem>>,
                response: Response<ArrayList<LearningResponseItem>>
            ){
                if(response.isSuccessful) {
                    showLoading(false)
                    val data = response.body()
                    if (data != null) {
                        setDataAdapter(data)
                    }
                }
            }
            override fun onFailure(call: Call<ArrayList<LearningResponseItem>>, t: Throwable) {
                Log.d("error", ""+t.stackTraceToString())
            }
        })
    }

    fun setDataAdapter(data: ArrayList<LearningResponseItem>) {
        learning.setData(data)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
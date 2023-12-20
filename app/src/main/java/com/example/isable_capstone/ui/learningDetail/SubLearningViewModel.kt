package com.example.isable_capstone.ui.learningDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.isable_capstone.model.Repository
import com.example.isable_capstone.response.AllAngkaResponseItem
import kotlinx.coroutines.launch

class SubLearningViewModel(private val repository: Repository): ViewModel() {


    fun getAll(modul: String) = repository.getAll(modul)

}
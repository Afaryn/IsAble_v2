package com.example.isable_capstone.ui.learningDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.isable_capstone.model.Repository
import com.example.isable_capstone.response.AllAngkaResponseItem
import kotlinx.coroutines.launch

class SubLearningViewModel(private val repository: Repository): ViewModel() {
//    private val _angkaList = MutableLiveData<List<AllAngkaResponseItem>>()
//    val angkaList: LiveData<List<AllAngkaResponseItem>> get() = _angkaList

    fun getAll(modul: String) = repository.getAll(modul)
//    fun getAllAngka(modul: String) {
//        viewModelScope.launch {
//            _angkaList.value = repository.getAll(modul)
//        }
//    }
}
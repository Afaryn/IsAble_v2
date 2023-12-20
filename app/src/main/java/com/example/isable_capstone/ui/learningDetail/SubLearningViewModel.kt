package com.example.isable_capstone.ui.learningDetail

import androidx.lifecycle.ViewModel
import com.example.isable_capstone.model.Repository


class SubLearningViewModel(private val repository: Repository): ViewModel() {


    fun getAll(modul: String) = repository.getAll(modul)

}
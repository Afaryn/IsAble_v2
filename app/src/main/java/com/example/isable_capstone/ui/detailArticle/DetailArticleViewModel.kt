package com.example.isable_capstone.ui.detailArticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.isable_capstone.model.Article
import com.example.isable_capstone.model.ArticleDataResource

class DetailArticleViewModel : ViewModel() {

    private var articleId: Int = -1
    private var _selectedArticle = MutableLiveData<Article>()
    val selectedArticle: LiveData<Article>
        get() = _selectedArticle

    fun setArticleId(id: Int) {
        if (articleId != id) {
            articleId = id
            loadArticleDetails()
        }
    }

    private fun loadArticleDetails() {
        _selectedArticle.value = ArticleDataResource.dummyArticle.find { it.id == articleId }
    }
}

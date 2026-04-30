package com.tuberosus.ayl.feature.news.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.repository.NewsRepository
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private var _state = MutableStateFlow(NewsListState())
    val state = _state.asStateFlow()

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            newsRepository.getNews()
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            news = result
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            news = null,
                            error = error
                        )
                    }
                }
        }
    }
}
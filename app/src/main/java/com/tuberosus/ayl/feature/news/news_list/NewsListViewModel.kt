package com.tuberosus.ayl.feature.news.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.repository.NewsRepository
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private var _state = MutableStateFlow(NewsListState())
    val state = _state.asStateFlow()

    init {
        observeNews()
    }

    fun onAction(action: NewsListAction) {
        when (action) {
            else -> Unit
        }
    }

    private fun getNews() {
        viewModelScope.launch {
            newsRepository.getNews()
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            news = result,
                            error = null,
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

    private fun observeNews() {
        newsRepository.observeNews()
            .onEach { result ->
                result
                    .onSuccess { news ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                news = news,
                                error = null
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
            .launchIn(viewModelScope)
    }
}
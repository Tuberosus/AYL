package com.tuberosus.ayl.feature.news.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.domain.repository.NewsRepository
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private var _state = MutableStateFlow(NewsListState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<NewsListEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        observeNews()
    }

    fun onAction(action: NewsListAction) {
        when (action) {
            NewsListAction.OnDeleteClick -> deleteNews()
            NewsListAction.OnDismissClick -> closeEditMenu()
            is NewsListAction.OnNewsLongClick -> openEditMenu(action.news)
            else -> Unit
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

    private fun deleteNews() {
        viewModelScope.launch {
            _state.value.selectedNews?.let { news ->
                newsRepository.deleteNews(news.id)
                    .onSuccess {
                        eventChannel.send(
                            NewsListEvent.InfoMessage(
                                "Новость удалена"
                            )
                        )
                    }
                    .onFailure {
                        eventChannel.send(
                            NewsListEvent.InfoMessage(
                                "Не удалось удалить новость"
                            )
                        )
                    }
            }
            closeEditMenu()
        }
    }

    private fun openEditMenu(news: News) {
        _state.update {
            it.copy(
                selectedNews = news,
                isEditMenuOpen = true,
            )
        }
    }

    private fun closeEditMenu() {
        _state.update {
            it.copy(
                isEditMenuOpen = false,
                selectedNews = null
            )
        }
    }
}
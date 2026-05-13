package com.tuberosus.ayl.feature.news.news_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.repository.NewsRepository
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsDetailsViewModel(
    private val newsRepository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val newsId = savedStateHandle.get<String>("newsId")
        ?: throw IllegalStateException("No newsId passed to NewsDetailsScreen")

    private val eventChannel = Channel<NewsDetailsEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(NewsDetailsState())
    val state = _state.asStateFlow()

    init {
        getNewsById(newsId)
    }

    fun onAction(action: NewsDetailsAction) {
        when (action) {
            is NewsDetailsAction.OnEventLinkClick ->
                sendOnMoreClickEvent(action.link)

            else -> Unit
        }
    }

    private fun getNewsById(newsId: String) {
        viewModelScope.launch {
            newsRepository.getNewsById(newsId)
                .onSuccess { news ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            news = news
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error
                        )
                    }
                }
        }
    }

    private fun sendOnMoreClickEvent(link: String) {
        if (link.isNotBlank()) {
            sendEvent(
                NewsDetailsEvent.OnEventLinkClick(link)
            )
        }
    }

    private fun sendEvent(event: NewsDetailsEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }
}
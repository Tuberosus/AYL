package com.tuberosus.ayl.feature.news.news_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.domain.usecase.SaveNewsUseCase
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsAdminViewModel(
    private val saveNewsUseCase: SaveNewsUseCase
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(NewsAdminState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeValidationStates()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = NewsAdminState()
        )

    private val eventChannel = Channel<NewsAdminEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: NewsAdminAction) {
        when (action) {
            is NewsAdminAction.OnNewsTextChange -> onNewsTextChange(action.value)
            is NewsAdminAction.OnPhotoUrlChange -> onPhotoUrlChange(action.value)
            is NewsAdminAction.OnSourceUrlChange -> onSourceUrlChange(action.value)
            is NewsAdminAction.OnTitleChange -> onTitleChange(action.value)
            NewsAdminAction.OnSave -> saveNews()
            NewsAdminAction.OnDismiss -> clearState()
        }
    }

    private fun observeValidationStates() {
        _state
            .map {
                it.title.isNotBlank()
                        && it.newsText.isNotBlank()
                        && !it.isSaving
            }
            .distinctUntilChanged()
            .onEach { canSave ->
                _state.update { it.copy(canSave = canSave) }
            }
            .launchIn(viewModelScope)
    }

    private fun saveNews() {
        val state = _state.value

        if (!state.canSave) return

        viewModelScope.launch {
            _state.update {
                it.copy(isSaving = true)
            }

            val news = News(
                id = "",
                title = state.title,
                content = state.newsText,
                imageUrl = state.photoUrl,
                linkUrl = state.sourceUrl,
                date = System.currentTimeMillis()
            )

            saveNewsUseCase(news)
                .onSuccess {
                    _state.update { NewsAdminState() }
                    eventChannel.send(NewsAdminEvent.SuccessSave)
                }
                .onFailure {
                    _state.update {
                        it.copy(isSaving = false)
                    }

                    eventChannel.send(
                        NewsAdminEvent.SaveErrorMessage(
                            "Ошибка при загрузке фотографий. Попробуйте позже."
                        )
                    )
                }
        }
    }

    private fun onTitleChange(value: String) {
        _state.update {
            it.copy(title = value)
        }
    }

    private fun onPhotoUrlChange(value: String) {
        _state.update {
            it.copy(photoUrl = value)
        }
    }

    private fun onSourceUrlChange(value: String) {
        _state.update {
            it.copy(sourceUrl = value)
        }
    }

    private fun onNewsTextChange(value: String) {
        _state.update {
            it.copy(newsText = value)
        }
    }

    private fun clearState() {
        _state.update { NewsAdminState() }
    }
}
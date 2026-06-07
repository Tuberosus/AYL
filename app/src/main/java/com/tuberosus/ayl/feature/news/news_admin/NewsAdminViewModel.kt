package com.tuberosus.ayl.feature.news.news_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.domain.usecase.SaveNewsUseCase
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import com.tuberosus.ayl.feature.news.news_admin.model.NewsDraft
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
            is NewsAdminAction.SetNewsForUpdate -> setNewsForUpdate(action.newsForUpdate)
        }
    }

    private fun observeValidationStates() {
        _state
            .map {
                it.newsDraft.title.isNotBlank()
                        && it.newsDraft.newsText.isNotBlank()
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
                id = state.newsDraft.id,
                title = state.newsDraft.title,
                content = state.newsDraft.newsText,
                imageUrl = state.newsDraft.photoUrl,
                linkUrl = state.newsDraft.sourceUrl,
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
                            "Ошибка при сохранении. Попробуйте позже."
                        )
                    )
                }
        }
    }

    private fun onTitleChange(value: String) {
        _state.update { currentState ->
            currentState.copy(
                newsDraft = currentState.newsDraft.copy(
                    title = value
                )
            )
        }
    }

    private fun onPhotoUrlChange(value: String) {
        _state.update { currentState ->
            currentState.copy(
                newsDraft = currentState.newsDraft.copy(
                    photoUrl = value
                ),
            )
        }
    }

    private fun onSourceUrlChange(value: String) {
        _state.update { currentState ->
            currentState.copy(
                newsDraft = currentState.newsDraft.copy(
                    sourceUrl = value
                )
            )
        }
    }

    private fun onNewsTextChange(value: String) {
        _state.update { currentState ->
            currentState.copy(
                newsDraft = currentState.newsDraft.copy(
                    newsText = value
                ),
            )
        }
    }

    private fun clearState() {
        _state.update { NewsAdminState() }
    }

    private fun setNewsForUpdate(news: News?) {
        _state.update { currentState ->
            currentState.copy(
                newsDraft = NewsDraft(
                    id = news?.id ?: "",
                    title = news?.title ?: "",
                    photoUrl = news?.imageUrl ?: "",
                    sourceUrl = news?.linkUrl ?: "",
                    newsText = news?.content ?: "",
                )
            )
        }
    }
}
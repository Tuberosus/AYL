package com.tuberosus.ayl.feature.news.news_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewsListViewModel : ViewModel() {
    private var _state = MutableStateFlow(NewsListState())
    val state = _state.asStateFlow()
}
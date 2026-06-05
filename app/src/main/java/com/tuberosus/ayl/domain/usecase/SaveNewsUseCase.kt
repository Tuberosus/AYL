package com.tuberosus.ayl.domain.usecase

import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.domain.repository.NewsRepository
import com.tuberosus.ayl.domain.util.Result

class SaveNewsUseCase(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(news: News): Result<Unit> {
        return newsRepository.saveNews(news)
    }
}
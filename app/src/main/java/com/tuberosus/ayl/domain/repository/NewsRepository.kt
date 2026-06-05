package com.tuberosus.ayl.domain.repository

import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.domain.util.Result

interface NewsRepository {
    suspend fun getNews(): Result<List<News>>
    suspend fun getNewsById(newsId: String): Result<News>
    suspend fun saveNews(news: News): Result<Unit>
}
package com.tuberosus.ayl.data.repository

import com.tuberosus.ayl.data.mapper.toNews
import com.tuberosus.ayl.data.remote.firestore.FirestoreRemoteDataSource
import com.tuberosus.ayl.data.remote.firestore.dto.NewsDto
import com.tuberosus.ayl.data.remote.firestore.getCollection
import com.tuberosus.ayl.data.remote.firestore.getDocument
import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.domain.repository.NewsRepository
import com.tuberosus.ayl.domain.util.Result
import com.tuberosus.ayl.domain.util.map

class NewsRepositoryImpl(
    private val firestoreRemoteDataSource: FirestoreRemoteDataSource
) : NewsRepository {
    private var cachedNews: List<News>? = null

    override suspend fun getNews(): Result<List<News>> {
        cachedNews?.let {
            return Result.Success(it)
        }

        return firestoreRemoteDataSource.getCollection<NewsDto>(NEWS_COLLECTION)
            .map { newsDtos ->
                val news = newsDtos
                    .map { it.toNews() }
                    .sortedByDescending { it.date }
                cachedNews = news
                news
            }
    }

    override suspend fun getNewsById(newsId: String): Result<News> {
        cachedNews
            ?.firstOrNull { it.id == newsId }
            ?.let { news ->
                return Result.Success(news)
            }

        return firestoreRemoteDataSource
            .getDocument<NewsDto>(
                collection = NEWS_COLLECTION,
                documentId = newsId
            )
            .map { newsDto ->
                newsDto.toNews()
            }
    }

    companion object {
        private const val NEWS_COLLECTION = "News"
    }
}
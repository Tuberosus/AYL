package com.tuberosus.ayl.data.repository

import com.tuberosus.ayl.data.mapper.toNews
import com.tuberosus.ayl.data.remote.firestore.FirestoreRemoteDataSource
import com.tuberosus.ayl.data.remote.firestore.dto.NewsDto
import com.tuberosus.ayl.data.remote.firestore.getCollection
import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.domain.repository.NewsRepository
import com.tuberosus.ayl.domain.util.Result
import com.tuberosus.ayl.domain.util.map

class NewsRepositoryImpl(
    private val firestoreRemoteDataSource: FirestoreRemoteDataSource
) : NewsRepository {
    override suspend fun getNews(): Result<List<News>> {
        return firestoreRemoteDataSource.getCollection<NewsDto>(NEWS_COLLECTION)
            .map { list -> list.map { it.toNews() } }
    }

    companion object {
        private const val NEWS_COLLECTION = "News"
    }
}
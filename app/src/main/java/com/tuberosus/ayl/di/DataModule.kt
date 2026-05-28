package com.tuberosus.ayl.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuberosus.ayl.data.remote.auth.FirebaseAuthDataSource
import com.tuberosus.ayl.data.remote.firestore.FirestoreRemoteDataSource
import com.tuberosus.ayl.data.repository.AuthRepositoryImpl
import com.tuberosus.ayl.data.repository.GalleryRepositoryImpl
import com.tuberosus.ayl.data.repository.NewsRepositoryImpl
import com.tuberosus.ayl.data.repository.StaffRepositoryImpl
import com.tuberosus.ayl.domain.repository.AuthRepository
import com.tuberosus.ayl.domain.repository.GalleryRepository
import com.tuberosus.ayl.domain.repository.NewsRepository
import com.tuberosus.ayl.domain.repository.StaffRepository
import org.koin.dsl.module

val dataModule = module {
    single {
        FirebaseFirestore.getInstance()
    }
    single {
        Firebase.auth
    }
    single {
        FirestoreRemoteDataSource(get())
    }
    single {
        FirebaseAuthDataSource(get())
    }
    single<StaffRepository> {
        StaffRepositoryImpl(get())
    }
    single<NewsRepository> {
        NewsRepositoryImpl(get())
    }
    single<GalleryRepository> {
        GalleryRepositoryImpl(get())
    }
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }
}
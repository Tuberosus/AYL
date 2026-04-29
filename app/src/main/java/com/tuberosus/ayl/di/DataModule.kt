package com.tuberosus.ayl.di

import com.google.firebase.firestore.FirebaseFirestore
import com.tuberosus.ayl.data.repository.StaffRepositoryImpl
import com.tuberosus.ayl.domain.repository.StaffRepository
import org.koin.dsl.module

val dataModule = module {
    single {
        FirebaseFirestore.getInstance()
    }
    single<StaffRepository> {
        StaffRepositoryImpl(get())
    }
}
package com.tuberosus.ayl.di

import com.tuberosus.ayl.domain.usecase.SaveNewsUseCase
import com.tuberosus.ayl.domain.usecase.SaveStaffUseCase
import com.tuberosus.ayl.domain.usecase.SaveToGalleryUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        SaveToGalleryUseCase(get())
    }
    factory {
        SaveNewsUseCase(get())
    }
    factory {
        SaveStaffUseCase(get())
    }
}
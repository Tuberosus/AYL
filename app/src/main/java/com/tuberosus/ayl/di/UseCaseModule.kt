package com.tuberosus.ayl.di

import com.tuberosus.ayl.domain.usecase.SaveToGalleryUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        SaveToGalleryUseCase(get())
    }
}
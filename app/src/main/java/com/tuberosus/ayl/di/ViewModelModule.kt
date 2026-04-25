package com.tuberosus.ayl.di

import com.tuberosus.ayl.feature.contacts.ContactsViewModel
import com.tuberosus.ayl.feature.gallery.GalleryViewModel
import com.tuberosus.ayl.feature.home.HomeViewModel
import com.tuberosus.ayl.feature.news.NewsViewModel
import com.tuberosus.ayl.feature.staff.StaffViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::GalleryViewModel)
    viewModelOf(::NewsViewModel)
    viewModelOf(::StaffViewModel)
    viewModelOf(::ContactsViewModel)
}
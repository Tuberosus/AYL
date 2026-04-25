package com.tuberosus.ayl.di

import com.tuberosus.ayl.feature.contacts.contacts_head.ContactsHeadViewModel
import com.tuberosus.ayl.feature.gallery.gallery_list.GalleryListViewModel
import com.tuberosus.ayl.feature.home.about.AboutViewModel
import com.tuberosus.ayl.feature.news.news_list.NewsListViewModel
import com.tuberosus.ayl.feature.staff.staff_list.StaffListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::AboutViewModel)
    viewModelOf(::GalleryListViewModel)
    viewModelOf(::NewsListViewModel)
    viewModelOf(::StaffListViewModel)
    viewModelOf(::ContactsHeadViewModel)
}
package com.tuberosus.ayl.app

import android.app.Application
import com.tuberosus.ayl.di.dataModule
import com.tuberosus.ayl.di.useCaseModule
import com.tuberosus.ayl.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AylApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AylApplication)
            modules(dataModule, useCaseModule, viewModelModule)
        }
    }
}
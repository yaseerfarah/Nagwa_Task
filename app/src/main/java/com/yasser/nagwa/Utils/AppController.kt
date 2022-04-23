package com.yasser.nagwa.Utils

import android.app.Activity
import android.app.Application
import com.yasser.nagwa.Dagger.Component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AppController : Application(), HasAndroidInjector {

    // public static Locale localeLanguage;
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }


}
package com.yasser.nagwa.Dagger.Component

import android.app.Application
import android.content.Context
import com.yasser.nagwa.Dagger.PresenterModule
import com.yasser.nagwa.Dagger.RepositoryModule
import com.yasser.nagwa.Utils.AppController
import com.yasser.nagwa.Utils.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ ActivityModule::class, AndroidSupportInjectionModule::class, RepositoryModule::class, PresenterModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder?

        @BindsInstance
        fun appContext(context: Context): Builder?

        fun build(): AppComponent?
    }


    fun inject(baseApplication: BaseApplication)

}
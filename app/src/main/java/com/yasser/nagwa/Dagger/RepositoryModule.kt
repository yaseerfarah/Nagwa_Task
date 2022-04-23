package com.yasser.nagwa.Dagger

import android.content.Context
import com.yasser.nagwa.Data.Repostitory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
 class RepositoryModule {

    @Singleton
    @Provides
    fun ProvideRepository(context: Context):Repostitory{
        return Repostitory(context)
    }

}
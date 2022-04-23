package com.yasser.nagwa.Utils

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import com.yasser.nagwa.BuildConfig
import com.yasser.nagwa.Dagger.Component.DaggerAppComponent
import com.yasser.nagwa.Utils.BaseApplication.Companion.sInstance
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject


/**
 * This is the Application class of the project. As we want to enable multi-dex inorder to
 * have greater quantity of methods, we are extending [MultiDexApplication] class here.
 * @property sInstance an instance of this Application class
 * @author Mohd. Asfaq-E-Azam Rifat
 */
class BaseApplication : MultiDexApplication(), HasAndroidInjector {

    // public static Locale localeLanguage;
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    init {
        sInstance = this
    }

    companion object {
        private lateinit var sInstance: BaseApplication

        /**
         * This method provides the Application context
         * @return [Context] application context
         */
        @JvmStatic
        fun getBaseApplicationContext(): Context {
            return sInstance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // DataUtils.getAndroidHashKey()

       // PushNotifications.start(applicationContext, "f551f126-0625-4b73-826d-b9bbdca16230");



        if (applicationContext != null) {
            if (BuildConfig.DEBUG) {
                initiateOnlyInDebugMode(applicationContext)
            }
            initiate(applicationContext)
        }
    }

    /**
     * This method only executes in debug build. Therefore, we can place our debug build specific
     * initialization here. i.e, logging library, app data watcher library etc.
     * */
    private fun initiateOnlyInDebugMode(context: Context) {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return super.createStackElementTag(element) +
                        " - Method:${element.methodName} - Line:${element.lineNumber}"
            }
        })
    }

    /**
     * This method executes in every build mode. Therefore, we can place our essential and common
     * initialization here. i.e, base repository, usable libraries etc
     * */
    private fun initiate(context: Context) {

        // Enabling database for resume support even after the application is killed:
        val config = PRDownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .build()
        PRDownloader.initialize(applicationContext, config)
        DaggerAppComponent.builder()
            .application(this)?.appContext(applicationContext)?.build()?.inject(this);

    }
    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }


    override fun attachBaseContext(base: Context?) {

        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}
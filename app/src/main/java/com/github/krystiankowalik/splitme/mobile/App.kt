package com.github.krystiankowalik.splitme.mobile

import android.app.Application
import com.facebook.stetho.Stetho
import com.github.krystiankowalik.splitme.mobile.di.AppComponent
import com.github.krystiankowalik.splitme.mobile.di.AppModule
import com.github.krystiankowalik.splitme.mobile.di.DaggerAppComponent
import timber.log.Timber


class App : Application() {


    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initTimber()
        initStetho()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }


    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    private fun initStetho() {
        val initializerBuilder = Stetho.newInitializerBuilder(this)

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        )

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this.applicationContext)
        )

        // Use the InitializerBuilder to generate an Initializer
        val initializer = initializerBuilder.build()

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer)
    }
}

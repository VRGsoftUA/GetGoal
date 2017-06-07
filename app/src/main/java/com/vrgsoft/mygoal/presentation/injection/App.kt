package com.vrgsoft.mygoal.presentation.injection

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.vrgsoft.mygoal.presentation.common.SettingsHelper
import io.realm.Realm
import io.realm.RealmConfiguration




class App : MultiDexApplication(), HasComponent<ApplicationComponent> {


    companion object {
        lateinit var applicationComponent: ApplicationComponent
        var settings: SettingsHelper = SettingsHelper()

    }

    override fun onCreate() {
        super.onCreate()
        settings.init(this)
        Realm.init(applicationContext)
        initializeInjector()
        initRealmConfiguration()
//        settings.setDisabledAds(false)//disable ads
    }




    private fun initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .dataModule(DataModule())
                .domainModule(DomainModule()).build();
    }

    private fun initRealmConfiguration() {
        val realmConfiguration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    override val component: ApplicationComponent
        get() = applicationComponent

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}

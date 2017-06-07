package com.vrgsoft.mygoal.presentation.injection

import javax.inject.Singleton

import dagger.Component
import io.realm.Realm

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, DataModule::class, DomainModule::class))
interface ApplicationComponent {
    fun provideRealm(): Realm
}

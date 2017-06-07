package com.vrgsoft.mygoal.presentation.injection

import dagger.Module
import dagger.Provides
import io.realm.Realm

@Module
class DataModule {
    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }


}

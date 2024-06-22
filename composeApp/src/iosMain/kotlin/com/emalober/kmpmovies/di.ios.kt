package com.emalober.kmpmovies

import com.emalober.kmpmovies.data.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val nativeModule: Module = module {
    single { getDatabaseBuilder() }
}
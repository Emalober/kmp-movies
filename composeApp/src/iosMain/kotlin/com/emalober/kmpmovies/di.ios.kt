package com.emalober.kmpmovies

import com.emalober.kmpmovies.data.IosRegionDataSource
import com.emalober.kmpmovies.data.RegionDataSource
import com.emalober.kmpmovies.data.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val nativeModule: Module = module {
    single { getDatabaseBuilder() }
    factoryOf(::IosRegionDataSource) bind RegionDataSource::class
}
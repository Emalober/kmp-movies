package com.emalober.kmpmovies

import android.location.Geocoder
import com.emalober.kmpmovies.data.AndroidRegionDataSource
import com.emalober.kmpmovies.data.RegionDataSource
import com.emalober.kmpmovies.data.database.getDatabaseBuilder
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val nativeModule: Module = module {
    single { getDatabaseBuilder(get()) }
    factory { Geocoder(get()) }
    factory { LocationServices.getFusedLocationProviderClient(androidApplication()) }
    factoryOf(::AndroidRegionDataSource) bind RegionDataSource::class
}
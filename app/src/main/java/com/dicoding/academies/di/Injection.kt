package com.dicoding.academies.di

import android.content.Context
import com.dicoding.academies.data.source.AcademyRepository
import com.dicoding.academies.data.source.remote.RemoteDataSource
import com.dicoding.academies.utils.JsonHelper

object Injection {

    fun provideRepository(context: Context): AcademyRepository {

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))

        return AcademyRepository.getInstance(remoteDataSource)
    }
}
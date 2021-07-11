package com.example.foody.data.repository

import com.example.foody.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class FoodRecipeRepository @Inject constructor(
    remoteDataSource: RemoteDataSource
) {

    val remote = remoteDataSource
}
package com.example.foody.ui.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.foody.data.model.RecipesEntity
import com.example.foody.data.model.FoodRecipe
import com.example.foody.data.repository.FoodRecipeRepository
import com.example.foody.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val repository: FoodRecipeRepository
) : AndroidViewModel(application) {

    /** ROOM DATABASE **/

    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readDatabase().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) = viewModelScope.launch {
        repository.local.insertRecipes(recipesEntity)
    }

    /** RETROFIT **/
    private val _recipesResponse: MutableLiveData<Resource<FoodRecipe>> = MutableLiveData()
    val recipesResponse: LiveData<Resource<FoodRecipe>> = _recipesResponse

    private val _searchedRecipesResponse: MutableLiveData<Resource<FoodRecipe>> = MutableLiveData()
    val searchedRecipesResponse: LiveData<Resource<FoodRecipe>> = _searchedRecipesResponse

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        _recipesResponse.value = Resource.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                _recipesResponse.value = handleFoodRecipesResponse(response)
                val foodRecipe = recipesResponse.value!!.data
                if (foodRecipe != null) {
                    offlineCacheRecipes(foodRecipe)
                }
            } catch (e: Exception) {
                _recipesResponse.value = Resource.Error("Recipes not found.")
            }
        } else {
            _recipesResponse.value = Resource.Error("No Internet Connection.")
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        _searchedRecipesResponse.value = Resource.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                _searchedRecipesResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                _searchedRecipesResponse.value = Resource.Error("Recipes not found.")
            }
        } else {
            _searchedRecipesResponse.value = Resource.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): Resource<FoodRecipe> {
        when {
            response.message().toString().contains("timeout") -> {
                return Resource.Error("Timeout")
            }
            response.code() == 402 -> {
                return Resource.Error("API key limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return Resource.Error("Recipes not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return Resource.Success(foodRecipes!!)
            }
            else -> {
                return Resource.Error(response.message().toString())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
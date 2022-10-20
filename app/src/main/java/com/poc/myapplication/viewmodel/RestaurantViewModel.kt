package com.poc.myapplication.viewmodel

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.poc.myapplication.Constants
import com.poc.myapplication.Utils
import com.poc.myapplication.model.Menu
import com.poc.myapplication.model.Menus
import com.poc.myapplication.model.Restaurant
import com.poc.myapplication.model.Restaurants
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class RestaurantViewModel: ViewModel() {

    private var restaurantData: Restaurant? = null
    private var menuData: Menu? = null


    private val searchState =
        MutableStateFlow<SearchState>(
            SearchState.Init
        )
    val mSearchState: StateFlow<SearchState> get() = searchState

    private var searchJob: Job? = null

    fun readFilesForData(assetManager: AssetManager?) {
        viewModelScope.launch {
            val restaurantString = Utils.loadFile(assetManager, Constants.RESTAURANT_JSON)
            val restaurantType = object : TypeToken<Restaurant>() {}.type
            restaurantData = Gson().fromJson(restaurantString, restaurantType)

            val menuString = Utils.loadFile(assetManager, Constants.MENU_JSON)
            val menuType = object : TypeToken<Menu>() {}.type
            menuData = Gson().fromJson(menuString, menuType)
            if(menuData != null && restaurantData != null) {
                //notify UI
            }
        }
    }




    fun fetchRestaurantsBasedOnSearch(searchString: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if (searchString.length >= Constants.SEARCH_MINIMUM_LETTERS) {
                val finalList: MutableList<Restaurants> = arrayListOf()
                val searchKey = searchString.lowercase()
                checkAndAddBasedOnRestaurant(searchKey, finalList)
                val menuItem = checkAndAddBasedOnMenu(searchKey, finalList)
                if (finalList.isEmpty()) {
                    searchState.value =
                        SearchState.NoDataState("No Restaurants Available")
                } else {
                    searchState.value = SearchState.SuccessState(finalList, searchKey, menuItem)
                }
            } else {
                if (searchString.isEmpty()) {
                    searchState.value =
                        SearchState.LessCharactersState("")
                } else {
                    searchState.value =
                        SearchState.LessCharactersState("Please type more than ${Constants.SEARCH_MINIMUM_LETTERS} letters")
                }
            }
        }
    }


    private fun checkAndAddBasedOnRestaurant(searchString: String, finalList: MutableList<Restaurants>) {
        val list = restaurantData?.restaurants
        list?.let {
            for (restaurant in it) {
                if (restaurant.name?.lowercase()?.contains(searchString) == true) {
                    finalList.add(restaurant)
                    continue
                }
                if (restaurant.cuisineType?.lowercase()?.contains(searchString) == true) {
                    finalList.add(restaurant)
                    continue
                }
            }
        }
    }

    private fun checkAndAddBasedOnMenu(searchString: String, finalList: MutableList<Restaurants>): String {
        val menus = menuData?.menus
        menus?.let { restaurants ->
            for (restaurant in restaurants) {
                val menuItem = checkForEachRestaurant(restaurant, searchString)
                if (menuItem.isNotEmpty()) {
                    restaurant.restaurantId?.let {
                        val restaurantToAdd = getRestaurantBasedOnId(it)
                        if (restaurantToAdd != null && !finalList.contains(restaurantToAdd)) {
                            finalList.add(restaurantToAdd)
                            return menuItem
                        }
                    }
                }
            }
        }
        return ""
    }

    private fun checkForEachRestaurant(restaurant: Menus, searchString: String): String {
        val categories = restaurant.categories
        for (category in categories) {
            if (category.name?.lowercase()?.contains(searchString) == true) {
                return category.name ?: ""
            }
            val items = category.menuItems
            for (item in items) {
                if (item.name?.lowercase()?.contains(searchString) == true) {
                    return item.name ?: ""
                }
            }
        }
        return ""
    }


    fun getRestaurantBasedOnId(id: Int): Restaurants? {
        restaurantData?.restaurants?.forEach { node ->
            if(node.id == id) {
                return node
            }
        }
        return null
    }

    fun getMenuBasedOnId(id: Int): Menus? {
        menuData?.menus?.forEach { node ->
            if(node.restaurantId == id) {
                return node
            }
        }
        return null
    }


    sealed class SearchState {
        object Init : SearchState()
        data class SuccessState(
            val restaurantList: List<Restaurants>,
            val searchString: String,
            val menuName: String
        ) : SearchState()

        data class NoDataState(val message: String) :
            SearchState()

        data class LessCharactersState(val message: String) :
            SearchState()
    }
}
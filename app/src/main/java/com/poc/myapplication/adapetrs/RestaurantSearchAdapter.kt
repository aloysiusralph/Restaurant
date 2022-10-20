package com.poc.myapplication.adapetrs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poc.myapplication.Utils
import com.poc.myapplication.databinding.RestaurantSearchBinding
import com.poc.myapplication.gone
import com.poc.myapplication.model.Restaurants
import com.poc.myapplication.visible

class RestaurantSearchAdapter(private val restaurantClickListener: RestaurantClickListener) :
    ListAdapter<Restaurants, RestaurantSearchAdapter.RestaurantViewHolder>(
        DiffUtils()
    ) {

    private var searchString: String = ""
    private var menuItem: String = ""

    inner class RestaurantViewHolder(
        private val restaurantSearchBinding: RestaurantSearchBinding,
        private val restaurantClickListener: RestaurantClickListener
    ) :
        RecyclerView.ViewHolder(restaurantSearchBinding.root) {

        fun bind(restaurant: Restaurants, searchString: String, menuItem: String) {
            restaurantSearchBinding.restaurantName.text =
                restaurant.name?.let { Utils.getSpannedString(it, searchString) }
            restaurantSearchBinding.restaurantCuisine.text =
                restaurant.cuisineType?.let { Utils.getSpannedString(it, searchString) }
            if (menuItem.isNotEmpty()) {
                restaurantSearchBinding.restaurantMenuItem.visible()
                restaurantSearchBinding.restaurantMenuItem.text = Utils.getSpannedString(menuItem, searchString)
            } else {
                restaurantSearchBinding.restaurantMenuItem.gone()
            }
            restaurantSearchBinding.root.setOnClickListener {
                restaurantClickListener.onRestaurantClick(restaurant)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val restaurantSearchBinding =
            RestaurantSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(restaurantSearchBinding, restaurantClickListener)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(currentList[position], searchString, menuItem)
    }


    fun setSearchString(searchString: String, menuItem: String) {
        this.searchString = searchString
        this.menuItem = menuItem
    }


}

interface RestaurantClickListener{
    fun onRestaurantClick(restaurant: Restaurants)
}


class DiffUtils : DiffUtil.ItemCallback<Restaurants>() {
    override fun areItemsTheSame(oldItem: Restaurants, newItem: Restaurants): Boolean {
        return oldItem.hashCode() == newItem.id
    }

    override fun areContentsTheSame(oldItem: Restaurants, newItem: Restaurants): Boolean {
        return oldItem.hashCode() == newItem.id
    }
}
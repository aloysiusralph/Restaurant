package com.poc.myapplication.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.poc.myapplication.Constants
import com.poc.myapplication.R
import com.poc.myapplication.adapetrs.RestaurantClickListener
import com.poc.myapplication.adapetrs.RestaurantSearchAdapter
import com.poc.myapplication.databinding.FragmentSearchBinding
import com.poc.myapplication.gone
import com.poc.myapplication.model.Restaurants
import com.poc.myapplication.viewmodel.RestaurantViewModel
import com.poc.myapplication.visible
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class SearchFragment : Fragment(), RestaurantClickListener  {

    private lateinit var fragmentSearchBinding: FragmentSearchBinding

    val restaurantViewModel: RestaurantViewModel by activityViewModels()

    var restaurantSearchAdapter: RestaurantSearchAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        restaurantViewModel.readFilesForData(context?.assets)
        // Inflate the layout for this fragment
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return fragmentSearchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTextChangeListener()
        addResponseObserver()
        initRecyclerView()
    }


    fun initRecyclerView() {
        restaurantSearchAdapter = RestaurantSearchAdapter(this)
        fragmentSearchBinding.searchResults.apply {
            adapter = restaurantSearchAdapter
        }
    }



    private fun addResponseObserver(){
        restaurantViewModel.mSearchState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .distinctUntilChanged()
            .onEach { state -> handleSearchState(state) }
            .launchIn(lifecycleScope)
    }


    private fun handleSearchState(state: Any) {
        when (state) {
            is RestaurantViewModel.SearchState.Init -> Unit
            is RestaurantViewModel.SearchState.NoDataState -> {
                fragmentSearchBinding.searchText.visible()
                fragmentSearchBinding.searchText.text = state.message
                fragmentSearchBinding.searchResults.gone()
            }
            is RestaurantViewModel.SearchState.SuccessState -> handleSuccessState(state.restaurantList, state.searchString, state.menuName)
            is RestaurantViewModel.SearchState.LessCharactersState -> {
                fragmentSearchBinding.searchText.visible()
                fragmentSearchBinding.searchText.text = state.message
                fragmentSearchBinding.searchResults.gone()
            }
        }
    }


    private fun handleSuccessState(list: List<Restaurants>, searchString: String, menuItem: String) {
        fragmentSearchBinding.searchResults.visible()
        fragmentSearchBinding.searchText.gone()
        restaurantSearchAdapter?.setSearchString(searchString, menuItem)
        restaurantSearchAdapter?.submitList(list)
    }


    private fun addTextChangeListener() {
        fragmentSearchBinding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun afterTextChanged(p0: Editable?) {
                restaurantViewModel.fetchRestaurantsBasedOnSearch(p0.toString())
            }

        })
    }

    override fun onRestaurantClick(restaurant: Restaurants) {
        findNavController().navigate(R.id.action_search_to_details,
            bundleOf(
                Constants.ID to restaurant.id))
    }

}
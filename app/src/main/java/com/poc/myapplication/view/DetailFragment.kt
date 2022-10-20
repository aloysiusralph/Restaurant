package com.poc.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.poc.myapplication.Constants
import com.poc.myapplication.R
import com.poc.myapplication.databinding.FragmentDetailBinding
import com.poc.myapplication.databinding.FragmentSearchBinding
import com.poc.myapplication.viewmodel.RestaurantViewModel


class DetailFragment : Fragment() {

    private lateinit var fragmentDetailBinding: FragmentDetailBinding

    val restaurantViewModel: RestaurantViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        return fragmentDetailBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.get(Constants.ID) as Int
        val restaurantData =  restaurantViewModel.getRestaurantBasedOnId(id)

        fragmentDetailBinding.restaurantName.text = restaurantData?.name
    }

}
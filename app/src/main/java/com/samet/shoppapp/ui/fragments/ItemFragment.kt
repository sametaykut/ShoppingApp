package com.samet.shoppapp.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.samet.shoppapp.R
import com.samet.shoppapp.databinding.FragmentItemBinding
import com.samet.shoppapp.db.ItemDatabase
import com.samet.shoppapp.models.ProductItem
import com.samet.shoppapp.models.ProductItemFavourite
import com.samet.shoppapp.models.ProductResponse
import com.samet.shoppapp.repository.ProductRepository
import com.samet.shoppapp.ui.viewmodel.ProductViewModel
import com.samet.shoppapp.ui.viewmodel.ProductViewModelProvider
import com.samet.shoppapp.util.Constants
import com.samet.shoppapp.util.toProductItemFavourite

class ItemFragment : Fragment() {
    private lateinit var binding:FragmentItemBinding
    lateinit var viewModel: ProductViewModel
    val args: ItemFragmentArgs by navArgs()
    var clickCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemBinding.inflate(inflater, container, false)
        val repository = ProductRepository(ItemDatabase(requireContext()))
        val viewModelProviderFactory = ProductViewModelProvider(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ProductViewModel::class.java)


        val item  = args.item
        val favouriteItem = item.toProductItemFavourite()
        Glide.with(this).load(item.image).into(binding.ivItem)
        binding.tvItemTitle.text = item.title
        binding.tvItemCategory.text = item.category
        binding.tvItemDescription.text = item.description
        binding.tvItemPrice.text = item.price.toString()

        val  favori = Constants.getInt(requireContext(),"favouriteIcon",0)
        if (favori == 0 ){
            binding.favourite.isVisible = false
        }else{
            binding.favourite.isVisible = true
        }

        binding.buttonAddBox.setOnClickListener {
            viewModel.addToBox(item)
            it.isEnabled=false
            Toast.makeText(requireContext(),"Added Box",Toast.LENGTH_LONG).show()
        }

        binding.favourite.setOnClickListener {
            clickCount++


                if (clickCount % 2 == 1) {
                    binding.favourite.setImageResource(R.drawable.baseline_redfavoriteicon_24)
                    viewModel.addToFavourite(favouriteItem)
                } else {
                    binding.favourite.setImageResource(R.drawable.baseline_favorite_border_24)
                    viewModel.deleteToFavourite(favouriteItem)
                }
        }
        return binding.root
    }


}
package com.samet.shoppapp.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.samet.shoppapp.R
import com.samet.shoppapp.adapters.ShoppingItemsAdapter
import com.samet.shoppapp.databinding.FragmentFavouriteBinding
import com.samet.shoppapp.db.ItemDatabase
import com.samet.shoppapp.repository.ProductRepository
import com.samet.shoppapp.ui.viewmodel.ProductViewModel
import com.samet.shoppapp.ui.viewmodel.ProductViewModelProvider
import com.samet.shoppapp.util.Constants
import com.samet.shoppapp.util.toProductItemFavourite

class FavouriteFragment : Fragment() {
    private lateinit var binding : FragmentFavouriteBinding
    lateinit var shoppingItemsAdapter: ShoppingItemsAdapter
    lateinit var viewModel: ProductViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val productRepository = ProductRepository(ItemDatabase(requireContext()))
        val viewModelProviderFactory = ProductViewModelProvider(requireActivity().application, productRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(ProductViewModel::class.java)

        setupRecyclerView()
        viewModel.getAllIFavouritetems().observe(viewLifecycleOwner, Observer {
            shoppingItemsAdapter.differ.submitList(it)
            if (it.isEmpty()){
                binding.noItems.isVisible = true
                }else{
                binding.noItems.isVisible = false
                }
        })

        shoppingItemsAdapter.setOnClickListener {
            val action = FavouriteFragmentDirections.actionFavouriteFragmentToItemFragment(it)
            findNavController().navigate(action)
        }

        Constants.saveInt(requireContext(),"favouriteIcon",0)

        val itemTouchHelperCallBack =object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position =viewHolder.adapterPosition
                val item = shoppingItemsAdapter.differ.currentList[position]
                viewModel.deleteToFavourite(item.toProductItemFavourite())
                Snackbar.make(view!!,"Removed from favourites", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.addToFavourite(item.toProductItemFavourite())
                    }
                        .show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvFavourite)
        }


        return binding.root
    }

    private fun setupRecyclerView() {
        shoppingItemsAdapter = ShoppingItemsAdapter()
        binding.rvFavourite.apply {
            adapter = shoppingItemsAdapter
            layoutManager = LinearLayoutManager(activity)
        }


    }

}
package com.samet.shoppapp.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.samet.shoppapp.adapters.ShoppingItemsAdapter
import com.samet.shoppapp.databinding.FragmentProductsBinding
import com.samet.shoppapp.db.ItemDatabase
import com.samet.shoppapp.repository.ProductRepository
import com.samet.shoppapp.ui.viewmodel.ProductViewModel
import com.samet.shoppapp.ui.viewmodel.ProductViewModelProvider
import com.samet.shoppapp.util.Constants

class ProductsFragment : Fragment() {

    lateinit var shoppingItemsAdapter: ShoppingItemsAdapter
    private lateinit var binding: FragmentProductsBinding
    lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupShoppingItemsRecyclerView()

        val repository = ProductRepository(ItemDatabase(requireContext()))
        val viewModelProviderFactory = ProductViewModelProvider(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ProductViewModel::class.java)

        viewModel.observeProductList().observe(viewLifecycleOwner, Observer {response->
           shoppingItemsAdapter.differ.submitList(response)
        })

        viewModel.observeLoading().observe(viewLifecycleOwner, Observer {loading->
            binding.paginationProgressBar.isVisible= loading
        })

        viewModel.observeError().observe(viewLifecycleOwner, Observer {error->
            binding.tvError.isVisible= error
        })

        shoppingItemsAdapter.setOnClickListener{item->
            val action = ProductsFragmentDirections.actionProductsFragmentToItemFragment(item)
            findNavController().navigate(action)
        }

        Constants.saveInt(requireContext(),"favouriteIcon",1)
}


    private fun setupShoppingItemsRecyclerView() {
        shoppingItemsAdapter = ShoppingItemsAdapter()
        binding.rvProduct.apply {
            adapter = shoppingItemsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}
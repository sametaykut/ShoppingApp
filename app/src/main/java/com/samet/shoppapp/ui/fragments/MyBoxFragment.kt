package com.samet.shoppapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.samet.shoppapp.R
import com.samet.shoppapp.adapters.ShoppingItemsAdapter
import com.samet.shoppapp.databinding.FragmentMyBoxBinding
import com.samet.shoppapp.db.ItemDatabase
import com.samet.shoppapp.models.ProductItem
import com.samet.shoppapp.models.ProductResponse
import com.samet.shoppapp.repository.ProductRepository
import com.samet.shoppapp.ui.viewmodel.ProductViewModel
import com.samet.shoppapp.ui.viewmodel.ProductViewModelProvider

class MyBoxFragment : Fragment() {
    private lateinit var binding: FragmentMyBoxBinding
    lateinit var shoppingItemsAdapter: ShoppingItemsAdapter
    lateinit var viewModel: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentMyBoxBinding.inflate(inflater, container, false)
        val repository = ProductRepository(ItemDatabase(requireContext()))
        val viewModelProviderFactory = ProductViewModelProvider(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ProductViewModel::class.java)
        setupRecyclerView()
        viewModel.getAllItemsInBox().observe(viewLifecycleOwner, Observer {
            shoppingItemsAdapter.differ.submitList(it)
            if (it.isNotEmpty()){
                binding.buyButton.isVisible = true
                binding.noItemsBox.isVisible = false
                binding.buyButton.setOnClickListener {
                    val action = MyBoxFragmentDirections.actionMyBoxFragmentToLastPageFragment()
                    findNavController().navigate(action)
                    viewModel.deleteAllItems()
                }
            }else{
                binding.buyButton.isVisible = false
                binding.noItemsBox.isVisible = true
            }
        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = shoppingItemsAdapter.differ.currentList[position]
                viewModel.deleteToBox(item)
                Snackbar.make(view,"Removed from box", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.addToBox(item)
                    }
                        .show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvMyBox)
        }


    }

    private fun setupRecyclerView() {
        shoppingItemsAdapter = ShoppingItemsAdapter()
        binding.rvMyBox.apply {
            adapter = shoppingItemsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
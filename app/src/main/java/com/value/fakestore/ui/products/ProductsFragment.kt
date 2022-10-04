package com.value.fakestore.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.domain.common.model.Product
import com.domain.common.viewstate.products.ProductsStateResult
import com.domain.core.viewstate.BaseVS
import com.domain.core.viewstate.Failure
import com.domain.core.viewstate.Loading
import com.value.fakestore.R
import com.value.fakestore.base.BaseFragment
import com.value.fakestore.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductsFragment : BaseFragment<ProductsViewModel, ProductsIntent>() {

    private lateinit var binding: FragmentProductsBinding

    private lateinit var productsAdapter: ProductsAdapter

    // RecyclerView.VERTICAL for grid mode
    // RecyclerView.HORIZONTAL for list mode
    private var showMode: Int = RecyclerView.VERTICAL
        set(value) {
            binding.toolbar.menu?.findItem(R.id.mode_grid)?.isVisible =
                value == RecyclerView.HORIZONTAL
            binding.toolbar.menu?.findItem(R.id.mode_list)?.isVisible =
                value == RecyclerView.VERTICAL
            binding.list.layoutManager =
                if (value == RecyclerView.VERTICAL) {
                    GridLayoutManager(requireContext(), 2)
                } else {
                    LinearLayoutManager(requireContext()).apply {
                        orientation = RecyclerView.VERTICAL
                    }
                }
            productsAdapter.listViewType = value
            field = value
        }

    override fun initViews(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initActions(view: View) {
        super.initActions(view)

        // Set the adapter
        productsAdapter = ProductsAdapter(::onProductClicked)
        binding.list.run {
            layoutManager = if (showMode == RecyclerView.VERTICAL) {
                GridLayoutManager(requireContext(), 2)
            } else {
                LinearLayoutManager(requireContext()).apply {
                    orientation = RecyclerView.VERTICAL
                }
            }
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = productsAdapter
        }

        showMode = RecyclerView.VERTICAL

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.mode_grid -> true.also { showMode = RecyclerView.VERTICAL }
                R.id.mode_list -> true.also { showMode = RecyclerView.HORIZONTAL }
                else -> false
            }

        }

        sendIntent(ProductsIntent.GetAllProducts)
        binding.swipeRefresh.setOnRefreshListener {
            sendIntent(ProductsIntent.GetAllProducts)
        }
    }

    private fun onProductClicked(product: Product) {
        findNavController().navigate(ProductsFragmentDirections.actionToProductDetails(product))
    }

    override fun render(state: BaseVS) {
        super.render(state)

        when (state) {
            is Loading -> {
                binding.swipeRefresh.isRefreshing = true
                binding.list.isVisible = false
                binding.error.isVisible = false
            }
            is Failure -> {
                binding.swipeRefresh.isRefreshing = false
                binding.list.isVisible = false
                binding.error.isVisible = true
                binding.error.text = state.message
            }

            is ProductsStateResult -> {
                binding.swipeRefresh.isRefreshing = false

                binding.list.isVisible = state.products.isNotEmpty()
                binding.error.isVisible = state.products.isEmpty()
                if (state.products.isEmpty()) {
                    binding.error.text = "Sorry No Data Founded"
                } else {
                    productsAdapter.items = state.products.toMutableList()
                }
            }
        }
    }
}
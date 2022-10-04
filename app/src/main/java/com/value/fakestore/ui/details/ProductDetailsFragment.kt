package com.value.fakestore.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.value.fakestore.R
import com.value.fakestore.base.DefaultBaseFragment
import com.value.fakestore.databinding.FragmentProductDetailsBinding
import com.value.fakestore.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductDetailsFragment : DefaultBaseFragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun initViews(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        binding.product = args.product
        return binding.root
    }

    override fun initActions(view: View) {
        super.initActions(view)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }


}
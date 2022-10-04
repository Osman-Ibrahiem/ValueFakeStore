package com.value.fakestore.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.data.cache.source.UserModule
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

open class DefaultBaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var userModule: UserModule

    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (root == null) {
            root = initViews(inflater, container, savedInstanceState)
            root?.let { initActions(it) }
        }
        (root?.parent as? ViewGroup?)?.removeView(root)
        return root
    }

    open fun initViews(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    open fun initActions(view: View) {

    }
}
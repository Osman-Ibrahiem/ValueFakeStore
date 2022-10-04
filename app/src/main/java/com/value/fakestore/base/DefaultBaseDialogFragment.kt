package com.value.fakestore.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.data.cache.source.UserModule
import javax.inject.Inject

open class DefaultBaseDialogFragment : DialogFragment {

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    @Inject
    lateinit var userModule: UserModule

}
package com.salvacano.counterstuff.presentation.common.view.dialog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class LastTimeBaseBottomSheetDialogFragment<U: ViewDataBinding>: BottomSheetDialogFragment() {

    abstract val layoutId: Int
    lateinit var binding: U

    abstract fun U.initialize()
    open fun initialize() { }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<U>(inflater, layoutId, container, true).apply { initialize() }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (view?.parent as View).setBackgroundColor(Color.TRANSPARENT)
        initialize()
    }

}

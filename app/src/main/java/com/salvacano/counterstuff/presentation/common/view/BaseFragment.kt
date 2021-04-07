package com.salvacano.counterstuff.presentation.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.databinding.ViewDataBinding
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.salvacano.counterstuff.presentation.common.viewmodel.CounterStuffBaseViewModel

abstract class BaseFragment<T : CounterStuffBaseViewModel, U : ViewDataBinding> : Fragment() {

    protected abstract val viewModel: T
    protected lateinit var binding: U
    protected abstract val layoutId: Int
    protected abstract fun U.initialize()

    abstract val toolbarRes: Int
    open var showBackArrow = true

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = run {
        binding = DataBindingUtil.inflate<U>(inflater, layoutId, container, false).apply { initialize() }
        binding.lifecycleOwner = this
        binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
    }

    abstract fun initialize()

    fun showMessage(container: View, message: Int) {
        Snackbar.make(container, getString(message), Snackbar.LENGTH_SHORT).show()
    }

    fun <U> LiveData<U>.observe(newValue: (U) -> Unit) {
        this.observe(this@BaseFragment, Observer { newValue(it) })
    }

}
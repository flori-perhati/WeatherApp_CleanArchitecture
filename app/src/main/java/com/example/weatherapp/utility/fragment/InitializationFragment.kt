package com.example.weatherapp.utility.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.weatherapp.BR
import androidx.lifecycle.ViewModel
import com.example.weatherapp.utility.extension.progressDialog
import com.example.weatherapp.utility.permission.PermissionManager
import java.lang.ref.WeakReference


/**
 * Used to implement the fragment initialization pattern used in this project.
 *
 * layoutId represent the xml file for the fragment
 * viewModel represent the viewModel class for the fragment
 * viewBinding represent the auto-generated dataBinding file for the fragment
 *
 * Since this project is based on dataBinding, the viewModel has to be given
 * as a reference for each fragment in its xml file.
 */
abstract class InitializationFragment<VB: ViewDataBinding, VM: ViewModel>: Fragment() {

    /**
     * Handles the common pattern of fragment view creation and vieModel reference.
     */
    @get:LayoutRes
    protected abstract val layoutId: Int
    protected abstract val viewModel: VM
    protected lateinit var viewBinding: VB

    /**
     * Notifies that fragment is ready.
     */
    protected abstract fun onReady(savedInstanceState: Bundle?)

    protected lateinit var progressDialog: Dialog
    protected lateinit var permissionManager: PermissionManager<Fragment>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //
        progressDialog = requireContext().progressDialog()
        permissionManager = PermissionManager(WeakReference(this))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        viewBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, viewModel)
        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        onReady(savedInstanceState)
    }
}
package kaan.cashapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class ViewBindFragment<VM : ViewModel, VB : ViewBinding> : Fragment() {
    protected abstract val vm: VM
    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = getViewBinding(inflater, container)

        return binding.root
    }

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}

package kaan.cashapp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class ViewBindActivity<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) : AppCompatActivity() {
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }
}

package kaan.cashapp.extra

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import kaan.cashapp.ui.home.models.Stock



@BindingAdapter("errorText")
fun setErrorText(view: TextInputLayout, errorMessage: String) {
    if (errorMessage.isEmpty())
        view.error = null
    else
        view.error = errorMessage;
}

@BindingAdapter("text")
fun setText(view: TextView, stock: Stock?) {
    stock?.let {
        view.text = stock.ticker
    }
}
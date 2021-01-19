package az.squareroot.customstaxescalc

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import az.squareroot.customstaxescalc.database.datastructure.Carrier
import net.colindodd.gradientlayout.GradientRelativeLayout
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("bindPrice")
fun bindPrice(textView: TextView, data: Carrier?) {
    if (data != null) {
        textView.visibility = View.VISIBLE
        val min: Double = data.prices[0].price
        val max: Double = data.prices[data.prices.size - 1].price
        textView.text = textView.resources.getString(R.string.label_carrier_price, min, max)
    } else {
        textView.visibility = View.GONE
    }
}

@BindingAdapter("bindWarehouses")
fun bindWarehouses(textView: TextView, data: Carrier?) {
    if (data != null) {
        textView.visibility = View.VISIBLE
        var text: String = ""
        for (i in data.warehouses.indices) {
            text += data.warehouses[i]
            if (i != data.warehouses.size - 1) {
                text += ", "
            }
        }
        textView.text = textView.resources.getString(R.string.label_carrier_warehouse, text)
    } else {
        textView.visibility = View.GONE
    }
}

@BindingAdapter("bindImage")
fun bindImage(imageView: ImageView, data: Carrier?) {
    if (data != null) {
        imageView.visibility = View.VISIBLE
        imageView.setBackgroundResource(data.imageId)
    } else {
        imageView.visibility = View.GONE
        //imageView.setBackgroundResource(R.drawable.logo_placeholder)
    }
}

@SuppressLint("Range")
@BindingAdapter("bindColor")
fun bindColor(layout: GradientRelativeLayout, data: Carrier?) {
    if (data != null) {
        layout.setStartColor(Color.parseColor(data.startColor))
        layout.setEndColor(Color.parseColor(data.endColor))
    } else {
        layout.setStartColor(Color.parseColor("#000000"))
        layout.setEndColor(Color.parseColor("#000000"))
    }
}

@BindingAdapter("bindVisibility")
fun bindVisibility(textView: TextView, data: Carrier?) {
    if (data == null) {
        textView.visibility = View.VISIBLE
    } else {
        textView.visibility = View.GONE
    }
}

@BindingAdapter("bindTotalPrice")
fun bindTotalPrice(textView: TextView, data: Double) {
    textView.text = textView.resources.getString(R.string.label_calculation_price, data)
}

@BindingAdapter("bindTaxes")
fun bindTaxes(textView: TextView, data: Double) {
    textView.text = textView.resources.getString(R.string.label_calculation_taxes, data)
}

@BindingAdapter("bindCargoPrice")
fun bindCargoPrice(textView: TextView, data: Double) {
    textView.text = textView.resources.getString(R.string.label_cargo_price, data)
}

@BindingAdapter("bindDate")
fun bindDate(textView: TextView, data: Date) {
    textView.text = textView.resources.getText(R.string.label_total_price_to_pay, formatDate(data))
}

@SuppressLint("SimpleDateFormat")
private fun formatDate(dateObject: Date): String {
    val dateFormat = SimpleDateFormat("LLL dd, yyyy")
    return dateFormat.format(dateObject)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
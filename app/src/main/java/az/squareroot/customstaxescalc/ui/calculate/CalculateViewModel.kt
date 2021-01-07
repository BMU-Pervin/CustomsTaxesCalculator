package az.squareroot.customstaxescalc.ui.calculate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import az.squareroot.customstaxescalc.database.Carriers

class CalculateViewModel : ViewModel() {

    private var _itemPrice = MutableLiveData<Double>().apply {
        value = 0.0
    }
    val itemPrice: LiveData<Double> = _itemPrice

    private var _cargoPrice = MutableLiveData<Double>().apply {
        value = 0.0
    }
    val cargoPrice: LiveData<Double> = _cargoPrice

    private var _vat = MutableLiveData<Double>().apply {
        value = 0.0
    }
    val vat: LiveData<Double> = _vat

    private var _itax = MutableLiveData<Double>().apply {
        value = 0.0
    }
    val itax: LiveData<Double> = _itax

    private var _sf = MutableLiveData<Double>().apply {
        value = 0.0
    }
    val sf: LiveData<Double> = _sf

    private var _tc = MutableLiveData<Int>().apply {
        value = 0
    }
    val tc: LiveData<Int> = _tc

    private var _total = MutableLiveData<Double>().apply {
        value = 0.0
    }
    val total: LiveData<Double> = _total

    private var _totalPrice = MutableLiveData<Double>().apply {
        value = 0.0
    }
    val totalPrice: LiveData<Double> = _totalPrice

    fun calculate(itemPrice: Double, usedLimit: Double, cargoPrice: Double) {
        _itemPrice.value = itemPrice
        _cargoPrice.value = cargoPrice
        val price = (itemPrice + cargoPrice) * 1.7 - ((300 - usedLimit) * 1.7)
        if (price > 0) {
            _vat.value = price * 0.189
            _itax.value = price * 0.05
            _sf.value = 10.0
            _tc.value = when (price.toInt()) {
                in 1..999 -> 15
                in 1000..9999 -> 60
                in 10000..49999 -> 120
                in 50000..99999 -> 200
                in 100000..499999 -> 300
                in 500000..999999 -> 600
                else -> 1000
            }
            _total.value = vat.value!! + itax.value!! + sf.value!! + tc.value!!
        }
        else {
            _total.value = 0.0
            _vat.value = 0.0
            _itax.value = 0.0
            _sf.value = 0.0
            _tc.value = 0
        }

        _totalPrice.value = (itemPrice + cargoPrice) * 1.7 + total.value!!
    }

    fun calculate(itemPrice: Double, usedLimit: Double, itemWeight: Double, carrierId: Int) {
        val carrier = Carriers.INSTANCE[carrierId]
        val prices = carrier.prices
        val max = prices[prices.size - 1]
        var cargo = 0.0
        for (price in prices) {
            if (itemWeight in price.startWeight..price.endWeight) {
                if (price == max) {
                    cargo = price.price * itemWeight
                } else {
                    cargo = price.price
                }
                break
            }
        }
        calculate(itemPrice, usedLimit, cargo)
    }

    override fun onCleared() {
        Log.i("CalculateFragment.kt", "onClear")
        super.onCleared()
    }
}
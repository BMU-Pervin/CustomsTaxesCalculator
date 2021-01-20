package com.example.taxpay.ui.saved

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import az.squareroot.customstaxescalc.database.CarriersDatabase
import az.squareroot.customstaxescalc.database.datastructure.SavedCalculation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedViewModel(private val application: Application) : ViewModel() {
    private val db = CarriersDatabase.getInstance(application)
    private val _calculations = MutableLiveData<ArrayList<SavedCalculation>>().apply {
        CoroutineScope(Dispatchers.Main).launch {
            var list = ArrayList<SavedCalculation>()
            withContext(Dispatchers.IO) {
                list = db.savedCalculationDao.getAll() as ArrayList<SavedCalculation>
            }
            value = list
        }
    }
    val calculations: LiveData<ArrayList<SavedCalculation>> = _calculations
}
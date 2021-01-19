package az.squareroot.customstaxescalc.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import az.squareroot.customstaxescalc.R
import az.squareroot.customstaxescalc.SavedCalculationsAdapter
import az.squareroot.customstaxescalc.database.datastructure.SavedCalculation
import az.squareroot.customstaxescalc.databinding.FragmentSavedBinding
import com.example.taxpay.ui.saved.SavedViewModel
import java.util.*
import kotlin.collections.ArrayList

class SavedFragment : Fragment() {

    private lateinit var viewModel: SavedViewModel
    private lateinit var binding: FragmentSavedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.savedCalculationsList.adapter = SavedCalculationsAdapter()

        val list = ArrayList<SavedCalculation>()
        val myCalendar: Calendar = GregorianCalendar(2014, 2, 11)
        val myDate = myCalendar.time
        list.add(SavedCalculation(0, "Komputer", 128.0, 14.5, 1115.0, myDate))
        (binding.savedCalculationsList.adapter as SavedCalculationsAdapter).submitList(list)

        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SavedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
package az.squareroot.customstaxescalc.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import az.squareroot.customstaxescalc.SavedCalculationsAdapter
import az.squareroot.customstaxescalc.databinding.FragmentSavedBinding
import com.example.taxpay.ui.saved.SavedViewModel

class SavedFragment : Fragment() {

    private lateinit var viewModel: SavedViewModel
    private lateinit var binding: FragmentSavedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = SavedViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SavedViewModel::class.java)
        binding = FragmentSavedBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.savedCalculationsList.adapter = SavedCalculationsAdapter()

        viewModel.calculations.observe(viewLifecycleOwner) {
            (binding.savedCalculationsList.adapter as SavedCalculationsAdapter).submitList(it)
        }

        return binding.root
    }
}
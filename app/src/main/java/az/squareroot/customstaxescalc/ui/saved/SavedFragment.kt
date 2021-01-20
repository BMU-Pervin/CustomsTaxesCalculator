package az.squareroot.customstaxescalc.ui.saved

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import az.squareroot.customstaxescalc.ItemClickListener
import az.squareroot.customstaxescalc.SavedCalculationsAdapter
import az.squareroot.customstaxescalc.databinding.FragmentSavedBinding

class SavedFragment : Fragment(), ItemClickListener {

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

        binding.savedCalculationsList.adapter = SavedCalculationsAdapter(this)

        viewModel.calculations.observe(viewLifecycleOwner) {
            val adapter = binding.savedCalculationsList.adapter as SavedCalculationsAdapter
            Log.i("SavedFragment", "submit: ${it.size}")
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onClick(id: Int) {
        Log.i("SavedFragment", "delete")
        viewModel.deleteCalculation(id)
    }
}
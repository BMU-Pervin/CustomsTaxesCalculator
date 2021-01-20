package az.squareroot.customstaxescalc.ui.carriers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import az.squareroot.customstaxescalc.R
import az.squareroot.customstaxescalc.database.Carriers
import az.squareroot.customstaxescalc.CarriersAdapter
import az.squareroot.customstaxescalc.ItemClickListener
import az.squareroot.customstaxescalc.database.datastructure.Carrier
import az.squareroot.customstaxescalc.databinding.FragmentCarriersBinding

class CarriersFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentCarriersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarriersBinding.inflate(inflater)
        //val application = requireActivity().application
        //val viewModelFactory = CarriersViewModelFactory(application)
        //val viewModel = ViewModelProviders.of(this, viewModelFactory).get(CarriersViewModel::class.java)
        binding.lifecycleOwner = this

        binding.carriersList.adapter = CarriersAdapter()

        val toolbar:Toolbar = binding.toolbarCarriers as Toolbar
        val menuItem = toolbar.menu.findItem(R.id.menu_item_search)
        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        (binding.carriersList.adapter as CarriersAdapter).submitList(Carriers.INSTANCE)

        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return search(query)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return search(newText)
    }

    private fun search(newText: String?): Boolean {
        val userInput: String = newText!!.toLowerCase()
        val carriers: ArrayList<Carrier> = ArrayList()
        for (carrier in Carriers.INSTANCE) {
            if (carrier.name.toLowerCase().contains(userInput)) {
                carriers.add(carrier)
            }
        }
        (binding.carriersList.adapter as CarriersAdapter).submitList(carriers)
        return true
    }
}
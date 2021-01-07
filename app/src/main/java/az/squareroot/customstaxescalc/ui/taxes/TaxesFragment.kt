package az.squareroot.customstaxescalc.ui.taxes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import az.squareroot.customstaxescalc.R

class TaxesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragment: RelativeLayout =
            inflater.inflate(R.layout.fragment_taxes, container, false) as RelativeLayout

        //val toolbarCommon: Toolbar? = fragment.findViewById(R.id.toolbar_rules)

//        toolbarCommon!!.setNavigationOnClickListener {
//            val navController = findNavController()
//            navController.navigateUp()
//        }

        return fragment
    }
}
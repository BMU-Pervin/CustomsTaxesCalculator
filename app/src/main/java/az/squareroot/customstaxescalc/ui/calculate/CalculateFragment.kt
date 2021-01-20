package az.squareroot.customstaxescalc.ui.calculate

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import az.squareroot.customstaxescalc.R
import az.squareroot.customstaxescalc.database.Carriers
import az.squareroot.customstaxescalc.databinding.FragmentCalculateBinding
import az.squareroot.customstaxescalc.hideKeyboard
import com.google.android.material.snackbar.Snackbar


class CalculateFragment : Fragment(), SaveDialog.DialogListener {

    private lateinit var viewModel: CalculateViewModel
    private lateinit var binding: FragmentCalculateBinding
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(CalculateViewModel::class.java)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context)
        navController = (this as Fragment).findNavController()
        binding = FragmentCalculateBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        (binding.toolbarCalculate as Toolbar).setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_settings -> {
                    navController.navigate(R.id.action_navigation_calculate_to_navigation_settings)
                    true
                }
                R.id.menu_item_save -> {
                    val dialog = SaveDialog()
                    dialog.setTargetFragment(this, 0)
                    dialog.show(parentFragmentManager, "SaveDialog")
                    true
                }
                else -> false
            }
        }

        binding.buttonCalculate.setOnClickListener {
            try {
                binding.inputLayoutItemPrice.error = null
                binding.inputLayoutCargoPrice.error = null
                binding.inputLayoutItemWeight.error = null
                binding.inputLayoutUsedLimit.error = null
                val itemPrice = binding.inputTextItemPrice.text.toString().toDouble()
                var usedLimit = 0.0
                if (binding.checkboxLimitUsed.isChecked) {
                    try {
                        usedLimit = binding.inputTextUsedLimit.text.toString().toDouble()
                        if (usedLimit >= 300) {
                            (binding.inputTextUsedLimit as EditText).setText("300")
                            usedLimit = 300.0
                        }
                    } catch (e: NumberFormatException) {
                        binding.inputLayoutUsedLimit.error = getString(R.string.label_error_prices)
                        binding.checkboxLimitUsed.isChecked = false
                    }
                } else if (binding.checkboxLimitUsedFully.isChecked) {
                    usedLimit = 300.0
                }

                when (sharedPreferences.getString("calculation_type", "carrier")) {
                    "cargo_price" -> {
                        try {
                            val cargoPrice = binding.inputTextCargoPrice.text.toString().toDouble()
                            viewModel.calculate(itemPrice, usedLimit, cargoPrice)
                        } catch (e: NumberFormatException) {
                            binding.inputLayoutCargoPrice.error =
                                getString(R.string.label_error_prices)
                        }
                    }
                    "carrier" -> {
                        try {
                            val cargoWeight = binding.inputTextItemWeight.text.toString().toDouble()
                            val carrierId = sharedPreferences.getInt("selected_carrier", -1)
                            if (carrierId != -1) {
                                viewModel.calculate(itemPrice, usedLimit, cargoWeight, carrierId)
                            } else {
                                Toast.makeText(
                                    this.context,
                                    R.string.toast_unselected_carrier,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } catch (e: NumberFormatException) {
                            binding.inputLayoutItemWeight.error =
                                getString(R.string.label_error_prices)
                        }
                    }
                }
            } catch (e: NumberFormatException) {
                binding.inputLayoutItemPrice.error = getString(R.string.label_error_prices)
            }

            if (sharedPreferences.getBoolean("clean", false)) {
                (binding.inputTextItemPrice as EditText).setText("")
                (binding.inputTextCargoPrice as EditText).setText("")
            }
            binding.inputTextItemPrice.requestFocus()
            (binding.inputTextItemPrice as View).hideKeyboard()
        }

        binding.checkboxLimitUsed.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                false -> {
                    binding.inputLayoutUsedLimit.visibility = View.GONE
                    binding.checkboxLimitUsedFully.isEnabled = true
                }
                true -> {
                    binding.inputLayoutUsedLimit.visibility = View.VISIBLE
                    binding.checkboxLimitUsedFully.isEnabled = false
                }
            }
        }

        binding.checkboxLimitUsedFully.setOnCheckedChangeListener { _, isChecked ->
            binding.checkboxLimitUsed.isEnabled = !isChecked
        }

        when (sharedPreferences.getBoolean("limit", true)) {
            true -> {
                binding.checkboxLimitUsed.visibility = View.VISIBLE
                binding.checkboxLimitUsedFully.visibility = View.VISIBLE
            }
            false -> {
                binding.apply {
                    checkboxLimitUsed.visibility = View.GONE
                    checkboxLimitUsedFully.visibility = View.GONE
                    checkboxLimitUsed.isChecked = false
                    checkboxLimitUsedFully.isChecked = false
                }
            }
        }

        when (sharedPreferences.getBoolean("detailed", true)) {
            false -> {
                binding.apply {
                    labelVat.visibility = View.GONE
                    labelImportTax.visibility = View.GONE
                    labelTaxCollections.visibility = View.GONE
                    labelServiceFee.visibility = View.GONE
                }
            }
        }

        when (sharedPreferences.getString("calculation_type", "cargo_price")) {
            "carrier" -> {
                binding.apply {
                    inputLayoutCargoPrice.visibility = View.GONE
                    cardViewCarrier.visibility = View.VISIBLE
                    inputLayoutItemWeight.visibility = View.VISIBLE
                    labelCargoPrice.visibility = View.VISIBLE
                }
            }
            "cargo_price" -> {
                binding.apply {
                    inputLayoutCargoPrice.visibility = View.VISIBLE
                    inputLayoutItemWeight.visibility = View.GONE
                    cardViewCarrier.visibility = View.GONE
                    labelCargoPrice.visibility = View.GONE
                }
            }
        }

        when (sharedPreferences.getString("mass_unit", "kg")) {
            "kg" -> {
                binding.inputLayoutItemWeight.prefixText = "Kg  "
                viewModel.massUnit.value = CalculateViewModel.MassUnit.KG
            }
            "lbs" -> {
                binding.inputLayoutItemWeight.prefixText = "Lbs  "
                viewModel.massUnit.value = CalculateViewModel.MassUnit.LBS
            }
            "oz" -> {
                binding.inputLayoutItemWeight.prefixText = "Oz  "
                viewModel.massUnit.value = CalculateViewModel.MassUnit.OZ
            }
        }

        Carriers.liveDataInstance.observe(viewLifecycleOwner, {
            val carrierId = sharedPreferences.getInt("selected_carrier", -1)
            if (it == null || carrierId == -1) {
                binding.carrier = null

            } else {
                binding.carrier = it[carrierId]
            }
        })

        binding.cardViewCarrier.setOnClickListener {
            navController.navigate(R.id.navigation_carriers)
        }

        viewModel.cargoPrice.observe(viewLifecycleOwner, {
            binding.labelCargoPrice.text = getString(R.string.label_cargo_price, it)
        })

        viewModel.vat.observe(viewLifecycleOwner, {
            binding.labelVat.text = getString(R.string.label_vat, it)
        })

        viewModel.itax.observe(viewLifecycleOwner, {
            binding.labelImportTax.text = getString(R.string.label_import_tax, it)
        })

        viewModel.tc.observe(viewLifecycleOwner, {
            binding.labelTaxCollections.text =
                getString(R.string.label_tax_collections, it.toDouble())
        })

        viewModel.sf.observe(viewLifecycleOwner, {
            binding.labelServiceFee.text = getString(R.string.label_service_fee, it)
        })

        viewModel.total.observe(viewLifecycleOwner, {
            binding.labelTotal.text = getString(R.string.label_total, it)
        })

        viewModel.totalPrice.observe(viewLifecycleOwner, {
            binding.labelTotalPrice.text = getString(R.string.label_total_price_to_pay, it)
        })

        viewModel.name.observe(viewLifecycleOwner, {

            when {
                it == null -> {

                }
                it.isEmpty() -> {
                    Snackbar.make(binding.root, R.string.snackbar_dialog_error, Snackbar.LENGTH_LONG)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                        .show()
                }
                else -> {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        })

        return binding.root
    }

    override fun onFinishSaveDialog(name: String) {
        viewModel.name.value = name
    }

    override fun onStop() {
        binding.inputTextItemPrice.requestFocus()
        (binding.inputTextItemPrice as View).hideKeyboard()
        super.onStop()
    }
}
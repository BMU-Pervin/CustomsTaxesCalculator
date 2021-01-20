package az.squareroot.customstaxescalc.ui.calculate

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import az.squareroot.customstaxescalc.R

class SaveDialog : DialogFragment() {
    private lateinit var nameEditText: EditText
    private lateinit var dialogListener: DialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = LayoutInflater.from(context)
        val parentView = inflater.inflate(R.layout.dialog_save_calculate, null)

        builder
            .setView(parentView)
            .setTitle(requireContext().getString(R.string.label_dialog_save_calculation))
            .setMessage(requireContext().getString(R.string.label_dialog_save_calculation_message))
            .setPositiveButton(requireContext().getString(R.string.label_dialog_save_calculation_save)) { _, _ ->
                val name = nameEditText.text.toString()
                dialogListener.onFinishSaveDialog(name)
            }
            .setNegativeButton(requireContext().getString(R.string.label_dialog_save_calculation_cancel)) { _, _ ->

            }

        nameEditText = parentView.findViewById(R.id.input_text_dialog_calculation_name)

        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dialogListener = targetFragment as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Must implement DialogListener")
        }
    }

    interface DialogListener {
        fun onFinishSaveDialog(name: String)
    }
}
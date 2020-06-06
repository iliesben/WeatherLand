package com.example.weatherland.city

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.weatherland.R


class CreateCityDialogFragment : DialogFragment() {
    interface CreateCityDialogListener{
        fun onDialogPositiveClick(cityName: String)
        fun onDialogNegativeClick()
    }

    var listener: CreateCityDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)

        val input = EditText(context)
        with(input){
            inputType  = InputType.TYPE_CLASS_TEXT
            hint = context.getString(R.string.hintCityText)
        }
        builder.setTitle(R.string.createCityNameTitle)
                .setView(input)
                .setPositiveButton(R.string.createCityPositiveText,
                    DialogInterface.OnClickListener { _, _ ->
                        listener?.onDialogPositiveClick(input.text.toString())
                    })
                .setNegativeButton(R.string.createCityNegativeText,
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                        listener?.onDialogNegativeClick()
                    })


        return builder.create()
    }
}
package com.example.weatherland.city

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.weatherland.R

class DeleteCityDialogFragment : DialogFragment() {
    interface DeleteCityDialogListener{
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    companion object{

        val EXTRA_CITY_NAME = "com.example.weatherland.extras.EXTRA_CITY_NAME"

        fun newInstance(cityName: String) : DeleteCityDialogFragment {
            val fragment = DeleteCityDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_CITY_NAME,cityName)
            }
            return fragment
        }
    }

    var listener: DeleteCityDialogListener? = null

    private lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cityName = arguments!!.getString(EXTRA_CITY_NAME)!!
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.deleteCityTitle, cityName))
            .setPositiveButton(getString(R.string.deleteCityPositive)
            ) { _, _ -> listener?.onDialogPositiveClick() }

            .setNegativeButton(getString(R.string.deleteCityNegative)
            ) { _, _ -> listener?.onDialogNegativeClick()}


        return builder.create()
    }

}
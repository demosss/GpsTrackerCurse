package com.spbdemosss.gpstrackercurse.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.spbdemosss.gpstrackercurse.R

object DialogManager {
    fun showLocEnableDialog(context: Context){
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle(R.string.location_disabled)
        dialog.setMessage(context.getString(R.string.location_dialog_message))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes"){
            _, _ -> Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No"){
                _, _ -> Toast.makeText(context, "No", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }
}
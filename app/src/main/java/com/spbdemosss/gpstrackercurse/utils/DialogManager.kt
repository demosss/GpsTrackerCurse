package com.spbdemosss.gpstrackercurse.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Toast
import com.spbdemosss.gpstrackercurse.R
import com.spbdemosss.gpstrackercurse.databinding.SaveDialogBinding
import com.spbdemosss.gpstrackercurse.db.TrackItem

object DialogManager {
    fun showLocEnableDialog(context: Context, listener: Listener){
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle(R.string.location_disabled)
        dialog.setMessage(context.getString(R.string.location_dialog_message))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes"){
            _, _ ->listener.onClick()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No"){
                _, _ -> dialog.dismiss()
        }
        dialog.show()
    }

    fun showSaveDialog(context: Context, item: TrackItem?, listener: Listener){
        val builder = AlertDialog.Builder(context)
        val binding = SaveDialogBinding.inflate(LayoutInflater.from(context), null, false)
        builder.setView(binding.root)
        val dialog = builder.create()

        val time = "${item?.time}"
        val velocity = "Velocity: ${item?.velocity} km/h"
        val distance = "Distance: ${item?.distance} km"

        binding.tvTime.text = time
        binding.tvSpeed.text = velocity
        binding.tvDistance.text = distance

        binding.bSave.setOnClickListener {
            listener.onClick()
            dialog.dismiss()
        }
        binding.bCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    interface Listener{
        fun onClick()
    }
}
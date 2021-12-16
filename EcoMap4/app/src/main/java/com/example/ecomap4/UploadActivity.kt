package com.example.ecomap4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.ecomap4.databinding.ActivityUploadBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class UploadActivity (): AppCompatActivity(){

    private lateinit var binding: ActivityUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)}
        //    }


        // Get input text
        val input_location = binding.outlinedTextFieldLocation.editText?.text.toString()
        //val input_date = binding.outlinedTextFieldDate.editText?.text.toString()

        val sharedPreference = getSharedPreferences("CreateProfile", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()

        binding.DateInput.setOnClickListener{
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("날짜")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
                //.setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)

            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.time = Date(it)
                val calendarMilli = calendar.timeInMillis

                binding.DateInput.text = "${calendar.get(Calendar.YEAR)}/" +
                        "${calendar.get(Calendar.MONTH)}/" +
                        "${calendar.get(Calendar.DAY_OF_MONTH)}"
                }
            datePicker.show(supportFragmentManager,datePicker.toString())
        }

        val input_name = binding.outlinedTextFieldName.editText?.text.toString()
        val input_memo = binding.outlinedTextFieldMemo.editText?.text.toString()

//        if (input_date == null) {
//            binding.tipResult.text = ""
//            return
//        }

        binding.outlinedTextFieldLocation.editText?.doOnTextChanged { input_location, _, _, _ ->
            // Respond to input text change
        }
        //binding.DateInput.editText?.doOnTextChanged { input_date, _, _, _ ->
            // Respond to input text change
        //}
        binding.outlinedTextFieldName.editText?.doOnTextChanged { input_name, _, _, _ ->
            // Respond to input text change
        }
        binding.outlinedTextFieldMemo.editText?.doOnTextChanged { input_name, _, _, _ ->
            // Respond to input text change
        }

        binding.uploadSubmitButton.setOnClickListener{
            //setResult(mainIntent)
            finish()
        }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}
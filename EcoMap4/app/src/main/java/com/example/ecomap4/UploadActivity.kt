package com.example.ecomap4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
        var input_date: String = "Date"

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

                var myString = "${calendar.get(Calendar.YEAR)}/" +
                        "${calendar.get(Calendar.MONTH)}/" +
                        "${calendar.get(Calendar.DAY_OF_MONTH)}"
                binding.DateInput.text = myString
                input_date = myString
            }
            datePicker.show(supportFragmentManager,datePicker.toString())
        }

        val input_name = binding.outlinedTextFieldName.editText?.text.toString()
        val input_memo = binding.outlinedTextFieldMemo.editText?.text.toString()


        binding.outlinedTextFieldLocation.editText?.doOnTextChanged { input_location, _, _, _ ->
            // Respond to input text change
        }

        binding.uploadSubmitButton.setOnClickListener{
            if (input_date == "Date") {
                Toast.makeText(this, "날짜를 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
            else{
                val mainIntent: Intent = Intent(this, MainFragment::class.java)
                mainIntent.putExtra("date", input_date)
                mainIntent.putExtra("name", input_name)
                mainIntent.putExtra("memo", input_memo)
                setResult(RESULT_OK, mainIntent)
                Log.d("input!", "HI ")
                finish()
            }
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
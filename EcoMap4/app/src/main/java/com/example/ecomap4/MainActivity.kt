package com.example.ecomap4

import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val UserID= Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID) // context to this
    }

}
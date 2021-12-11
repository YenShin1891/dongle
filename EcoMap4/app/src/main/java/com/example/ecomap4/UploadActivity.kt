package com.example.ecomap4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomap4.databinding.ActivityUploadBinding

class UploadActivity (): AppCompatActivity(){

    private lateinit var binding: ActivityUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
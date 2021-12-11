package com.example.ecomap4

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.ecomap4.databinding.ActivityUploadBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class UploadActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding

    lateinit var filePath: String
    //Register a callback for activity result
    val camLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
        if (result == true) {
            executeAfterResult(filePath)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityUploadBinding.inflate(layoutInflater)

        takePicture()
        setContentView(binding.root)



    }

    private fun takePicture() {
        val toast = Toast.makeText(this, "Add Pin button works!", Toast.LENGTH_SHORT)
        toast.show()

        //Make file
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        filePath = file.absolutePath

        //Execute Camera with activity result launcher
        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "com.example.ecomap4.fileprovider", file
        )
        camLauncher.launch(photoURI)


    }

    private fun executeAfterResult(filePath : String){
        val toast = Toast.makeText(this, "Result is called back!", Toast.LENGTH_SHORT)
        toast.show()

        //accessPicture(filePath)
    }

    private fun accessPicture(filePath : String){
        //try bitmap
        val option = BitmapFactory.Options()
        option.inSampleSize = 10
        val bitmap = BitmapFactory.decodeFile(filePath, option)
        val toast_works = Toast.makeText(this, "Bitmap is not null!", Toast.LENGTH_SHORT)
        toast_works.show()
        bitmap?.let {
            //binding.expCapture.setImageBitmap(bitmap)
            //binding.expCapture.invalidate()
        }
    }



}
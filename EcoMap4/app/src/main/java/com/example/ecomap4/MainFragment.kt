package com.example.ecomap4

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.ecomap4.databinding.FragmentMainBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    lateinit var filePath: String
    //Register a callback for activity result
    val camLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
        if (result == true) {
            executeAfterResult(filePath)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPinButton.setOnClickListener{
            takePicture()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun takePicture() {
        val toast = Toast.makeText(context, "Add Pin button works!", Toast.LENGTH_SHORT)
        toast.show()

        //Make file
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        filePath = file.absolutePath

        //Execute Camera with activity result launcher
        val photoURI: Uri = FileProvider.getUriForFile(
            requireActivity(),
            "com.example.ecomap4.fileprovider", file
        )
        camLauncher.launch(photoURI)


    }

    private fun executeAfterResult(filePath : String){
        val toast = Toast.makeText(context, "Result is called back!", Toast.LENGTH_SHORT)
        toast.show()

        accessPicture(filePath)
    }

    private fun accessPicture(filePath : String){
        //try bitmap
        val option = BitmapFactory.Options()
        option.inSampleSize = 10
        val bitmap = BitmapFactory.decodeFile(filePath, option)
        val toast_works = Toast.makeText(context, "Bitmap is not null!", Toast.LENGTH_SHORT)
        toast_works.show()
        bitmap?.let {
            binding.expCapture.setImageBitmap(bitmap)
            binding.expCapture.invalidate()
        }
    }
}

/**
 * Content Provider for Camera and Gallery
 */
class MyContentProvider : ContentProvider() {
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

}




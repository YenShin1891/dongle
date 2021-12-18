package com.example.ecomap4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class ManagePic : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_pic)
        var memo_array = intent.getStringArrayExtra("selectedPinMemos")

        var j=0
//        for (memos in memo_array!!){
//            if (j==0){
//                Toast.makeText(applicationContext, "something's in the memo${memos}", Toast.LENGTH_SHORT).show()
//                findViewById<TextView>(R.id.fullText1).text=memos
//            }else if (j==1){
//                findViewById<TextView>(R.id.fullText2).text=memos
//            }else if (j==2){
//                findViewById<TextView>(R.id.fullText3).text=memos
//            }
//            j+=1
//        }
        j=0
//        for (pics in intent.getIntArrayExtra("selectedPinPictures")!!){
//            if (j==0){
//                findViewById<ImageView>(R.id.fullImage1).setImageResource(pics)
//            }else if (j==1){
//                findViewById<ImageView>(R.id.fullImage2).setImageResource(pics)
//            }else if (j==2){
//                findViewById<ImageView>(R.id.fullImage3).setImageResource(pics)
//            }
//            j+=1
//        }

    }
}
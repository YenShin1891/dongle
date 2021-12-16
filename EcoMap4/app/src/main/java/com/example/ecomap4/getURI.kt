package com.example.ecomap4

import android.content.ContentResolver
import android.content.Context
import android.net.Uri

class getURI {

    fun main (args: Array<String>) {
        //Int.getResourceUri(context)
    }

    fun Int.getResourceUri(context: Context): String {
        return context.resources.let {
            Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(it.getResourcePackageName(R.drawable.e001_01_1))        // it : resources, this : ResId(Int)
                .appendPath(it.getResourceTypeName(R.drawable.e001_01_1))        // it : resources, this : ResId(Int)
                .appendPath(it.getResourceEntryName(R.drawable.e001_01_1))        // it : resources, this : ResId(Int)
                .build()
                .toString()
        }
    }
}
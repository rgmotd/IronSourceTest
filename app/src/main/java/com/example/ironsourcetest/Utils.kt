package com.example.ironsourcetest

import android.content.Intent
import android.net.Uri

object Utils {

    fun getContactIntent(): Intent {
        val myData = "content://contacts/people/"
        return Intent(Intent.ACTION_VIEW, Uri.parse(myData))
    }
}
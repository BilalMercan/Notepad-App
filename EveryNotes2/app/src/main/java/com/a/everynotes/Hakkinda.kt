package com.a.everynotes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import java.util.*

class Hakkinda : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hakkinda)

        //Image View Tanımlaması (Buton olarak kullanılıyor)
        val backmain2 = findViewById<ImageView>(R.id.backmain2)

        //Geri butonuna basıldığında yapılacak işlemler
        backmain2.setOnClickListener {
            //OnbackPressed metodu ile bir önceki sayfaya(activity) e dönülmesi sağlanır.
           onBackPressed()
        }


    }

}
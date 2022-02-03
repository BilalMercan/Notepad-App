 package com.a.everynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.lang.System.exit
import kotlin.system.exitProcess

class Mainmenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmenu)

        //Buton tanımlamaları
        val notlar = findViewById<Button>(R.id.notlarim)
        val ayarlar = findViewById<Button>(R.id.ayarlar)
        val about = findViewById<Button>(R.id.hakkinda)
        val exit = findViewById<Button>(R.id.exitbtn)


        //Notlar butonuna basıldığında Notlar Listeleme sayfasına geçiş yapılıyor.
        notlar.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        //ayarlar butonuna basıldığında ayarlar sayfasına geçiş yapılıyor.
        ayarlar.setOnClickListener {
            val intent = Intent(this,Ayarlar::class.java)
            startActivity(intent)

        }

        //about butonuna basıldığında hakkında sayfasına geçiş yapılıyor.
        about.setOnClickListener {
            val intent = Intent(this,Hakkinda::class.java)
            startActivity(intent)

        }

        //exit butonuna basıldığında uygulamadan çıkış yapılması sağlanıyor.
        exit.setOnClickListener {
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            exit(1)
        }

    }
}
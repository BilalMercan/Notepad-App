package com.a.everynotes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Ayarlar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayarlar)
        val backmain3 = findViewById<ImageView>(R.id.backmain3)
        backmain3.setOnClickListener {
            val intent = Intent(this,Mainmenu::class.java)
            startActivity(intent)
        }
        val Language = findViewById<Button>(R.id.Language)

        //Uygulama her açıldığında kullanıcıdan seçtiği dili yükleyecek fonksiyon.
        LoadLanguage()

        //Butona basıldığında ekranda kullanıcıdan dil seçmesini isteniliyor.
        Language.setOnClickListener {
            //Dil ayarlar penceresinin açılması için fonksiyon çalıştırılıyor.
            ShowLanguageDialog()
        }
    }

    private fun ShowLanguageDialog() {

        val ListItems = arrayOf("English","Türkçe")

        val builder = AlertDialog.Builder(this@Ayarlar)
        builder.setTitle("Dil Seçiniz ")
        builder.setItems(ListItems) { dialog, which ->
            when (which) {
                0 ->{
                    SetLocale("en")      //String klasöründeki ingilizce dili için seçilen string dosyası
                    recreate()
                }
                1 ->{
                    SetLocale("tr")     //String klasöründeki türkçe dili için seçilen string dosyası
                    recreate()
                }
            }

            dialog.dismiss()

        }

        val mDialog:AlertDialog=builder.create()
        mDialog.show()
    }

    //"SetLocale" metodu oluşturuluyor. String veri tipinde
    private fun SetLocale(lang: String) {

        //
        val locale= Locale(lang)
        //Seçilen dilimizi bir sonraki girişlerde default dil haline getiriyoruz.
        Locale.setDefault(locale)
        val config:Configuration= Configuration()
        config.setLocale(locale)

        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
        //Ayrıca seçilen dili geçici olarak kayıt altına alıyoruz.
        val editor:SharedPreferences.Editor=getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang",lang)
        editor.apply()
    }


    fun LoadLanguage(){
        val pref: SharedPreferences =getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language:String= pref.getString("My_Lang","").toString()
        SetLocale(language)
    }

}
package com.a.everynotes

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_notes.*
import java.text.SimpleDateFormat
import java.util.*

class AddNotes : AppCompatActivity() {
    val dbTable="Notes"

    var id=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)


        //Buton tanımlamaları
        val eklebtn = findViewById<Button>(R.id.noteklebtn)

        val vazgecbtn = findViewById<Button>(R.id.vazgecbtn)

        //Textview tanımlası
        findViewById<TextView>(R.id.zamanvetarihtv)
        //textview'da saat ve tarih bilgileri yazdırılıyor.
        zamanvetarihtv.text = SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.getDefault()).format(Date())



       try{
           val bundle:Bundle= intent.extras!!
           id=bundle.getInt("ID",0)
           if(id!=0) {

               //not adı ile notları setText fonksiyonu ile verilerin not adı ve not bölümüne
               notbasligi.setText(bundle.getString("notetitle") )
               noticerigi.setText(bundle.getString("notes") )
           }
       }catch (ex:Exception){}


      eklebtn.setOnClickListener {

          if(notbasligi.text.isEmpty() && noticerigi.text.isEmpty()){
              Toast.makeText(this,"Not adı ve notunuzu giriniz",Toast.LENGTH_SHORT).show()
          }
          else{
              ekle()
              val intent= Intent(this, MainActivity::class.java)
              startActivity(intent)
          }

      }
        //OnbackPressed metodu ile bir önceki sayfaya(activity) e dönülmesi sağlanır.
        vazgecbtn.setOnClickListener {
          onBackPressed()
        }

    }




    private fun ekle() {

        //DbManager sınıfı burada da tanımlanır.
        val dbManager= DbManager(this)

        val values= ContentValues()

        values.put("Title",notbasligi.text.toString())
        values.put("Description",noticerigi.text.toString())

        if(id==0) {
            val ID = dbManager.Insert(values)
            if (ID > 0) {
                Toast.makeText(this, " notunuz eklendi ", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, " notunuz eklenemedi ", Toast.LENGTH_LONG).show()
            }
        }else{
                val selectionArgs= arrayOf(id.toString())
            val ID = dbManager.Update(values,"ID=?",selectionArgs)
            if (ID > 0) {
                Toast.makeText(this, " notunuz eklendi ", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, " notunuz eklenemedi ", Toast.LENGTH_LONG).show()
            }
        }
    }
}



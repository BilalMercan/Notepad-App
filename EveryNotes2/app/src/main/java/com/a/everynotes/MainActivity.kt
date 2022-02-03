package com.a.everynotes

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_notes.*
import kotlinx.android.synthetic.main.activity_add_notes.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.notlist.*
import kotlinx.android.synthetic.main.notlist.view.*
import kotlinx.android.synthetic.main.notlist.view.duzenlebtn
import kotlinx.android.synthetic.main.notlist.view.notaciklama
import kotlinx.android.synthetic.main.notlist.view.notbaslik
import kotlinx.android.synthetic.main.notlist.view.silbtn
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class MainActivity : AppCompatActivity() {


    //Notların gösterilmesi için oluşturulan diziyi "listNotes" adındaki değişkene atanarak Note sınıfındaki değişkenlerin tanımlanması sağlanır.
    var listNotes=ArrayList<Note>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Textview tanımlası
        val zamantv = findViewById<TextView>(R.id.zamantv)

        //textview'da saat ve tarih bilgileri yazdırılıyor.
        zamantv.text =  SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.getDefault()).format(Date())


          //Image View Tanımlaması (Buton olarak kullanılıyor)
          val yeninoteklebtn = findViewById<ImageView>(R.id.yeninotekle)

         //Not ekle Butonuna basıldığında yapılacak işlemler
         yeninoteklebtn.setOnClickListener {
             //Intent oluşturularak diğer sayfaya (activity) geçiş sağlanıyor.
             val intent = Intent(this, AddNotes::class.java)
             startActivity(intent)
         }

        LoadQuery("%")

        //Image View Tanımlaması (Buton olarak kullanılıyor)
        val backmain = findViewById<ImageView>(R.id.backmain1)

        //Geri butonuna basıldığında yapılacak işlemler
        backmain.setOnClickListener {
            onBackPressed()
        }


    }


    override fun onResume() {
        super.onResume()
        LoadQuery("%")
    }

    //Veri tabanına veri ekleme işlemi
    fun LoadQuery(title:String){

        //Veri tabanının tanımlanması
        val dbManager= DbManager(this)
        val projections= arrayOf("ID","Title","Description")
        val selectionArgs= arrayOf(title)
        val cursor=dbManager.Query(projections,"Title like ?",selectionArgs,"Title")
        listNotes.clear()
        if(cursor.moveToFirst()){
            do{
                val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                val Title=cursor.getString(cursor.getColumnIndex("Title"))
                val Description=cursor.getString(cursor.getColumnIndex("Description"))

                listNotes.add(Note(ID,Title,Description))
            }while (cursor.moveToNext())
        }
        //dizi ile bu sayfa içinde tanımlanan iç sınıf ile eşitlenir.
        val myNotesAdapter= MyNotesAdapter(this, listNotes)
        notesLv.adapter=myNotesAdapter

    }




    inner class  MyNotesAdapter(context: Context, var listNotesAdapter: ArrayList<Note>) :
        BaseAdapter() {
        var context:Context?= context

        @SuppressLint("ViewHolder", "InflateParams")

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val myView=layoutInflater.inflate(R.layout.notlist,null)

            val myNote=listNotesAdapter[position]

            myView.notbaslik.text=myNote.nodeName
            myView.notaciklama.text=myNote.nodeDes




            //Notların gösterildiği ekranda sil butonu ile not silme işlemi
            myView.silbtn.setOnClickListener{

                    val alert = AlertDialog.Builder(this@MainActivity)

                    // Başlık
                    alert.setTitle("Uyarı")
                    //Mesaj
                    alert.setMessage("Notunuzu silmek istediğinize emin misiniz?")

                    alert.setCancelable(false);

                      //setPositiveButton ile Gelen soruda evet butonuna tıklanıldığında verilerin silinmesi sağlanıyor.
                    alert.setPositiveButton("Evet") { dialogInterface: DialogInterface, i: Int ->
                        val dbManager= DbManager(this.context!!)
                        val selectionArgs= arrayOf(myNote.nodeID.toString())
                        dbManager.Delete("ID=?",selectionArgs)
                        LoadQuery("%")
                        Toast.makeText(applicationContext,"Notunuz silindi",Toast.LENGTH_LONG).show() }

                     //setNegativeButton ile Hayır butonuna tıklanınca olacaklar
                    alert.setNegativeButton("Hayır") {dialogInterface: DialogInterface, i: Int ->
                        Toast.makeText(applicationContext,"Notunuz silinmedi",Toast.LENGTH_LONG).show() }

                //dialog penceresinin kullanıcıya gösterilmesi sağlanır.
                alert.show()

            }

            //Notların gösterildiği sayfada Paylaş butonu
            myView.shareBtn.setOnClickListener {
                val title = myView.notbaslik.text.toString()
                val desc = myView.notaciklama.text.toString()

                val s = title+ "\n"+ desc

                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type= "text/plain"

                shareIntent.putExtra(Intent.EXTRA_TEXT, s)
                startActivity(Intent.createChooser(shareIntent, s))
            }

            //Notların gösterildiği ekranda düzenle butonu ile güncelleme işlemi
            myView.duzenlebtn.setOnClickListener{

                //Notların güncellenmesi için not ekleme ekranına gönderilmesi için kullanılan fonksiyon
                GoToUpdate(myNote)
            }


            return myView

        }


        override fun getItem(position: Int): Any {
        return listNotesAdapter[position]
         }
        override fun getItemId(p0: Int): Long {
           return p0.toLong()
         }
        override fun getCount(): Int {
            return listNotesAdapter.size
        }

    }

    //Not Güncelleme İşlemi
    fun GoToUpdate(Notlar: Note){
       val intent=  Intent(this, AddNotes::class.java)

       intent.putExtra("ID",Notlar.nodeID)
       intent.putExtra("notetitle",Notlar.nodeName)
       intent.putExtra("notes",Notlar.nodeDes)
       startActivity(intent)
   }


}

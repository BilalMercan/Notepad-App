package com.a.everynotes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast


class  DbManager(context: Context) {

    /* Veri Tabanı yapısı ve Sütun bilgileri*/
    val dbName="MyNotes"
    val dbTable="Notes"
    val colId="ID"
    val colTitle="Title"
    val colDes="Description"
    val dbVersion=1

    //Veri tabanı oluşturma sorgusu değişkene atanıyor. Veri tabanı oluşturuluyor. Kolon adları ve veri tipleri belirleniyor.

    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS $dbTable ($colId INTEGER PRIMARY KEY,$colTitle TEXT, $colDes TEXT);"

     //Sqlite veri tabanı tanımı
    var sqlDB:SQLiteDatabase?=null

    //init metodu ile çalıştırılacak işlemler yapılır.
    init {
        //Oluşturulan veri tabanının yazılabilir olması yani veri ekleme, yazma, silme işlemleri yapılması sağlanıyor.
        val db=DatabaseHelperNotes(context)
        sqlDB=db.writableDatabase
    }

    //Inner Class ile üst sınıfa veri tabanında yapılacak işlemler OnCreate ve onUpgrade fonsiyonları ile gönderiliyor.
    inner class  DatabaseHelperNotes(context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersion) {
         var context:Context?= context
        override fun onCreate(p0: SQLiteDatabase?) {
            //execSql metodu ile "sqlCreateTable" değişkeni parametre verilerek sql sorgusu çalıştırılıyor.
            p0!!.execSQL(sqlCreateTable)
            //Toast mesajı ile veri tabanı oluşturulduğuna dair mesaj veriliyor.
            Toast.makeText(this.context," veritabanı oluşturuldu ", Toast.LENGTH_LONG).show()
        }

        //Veri tabanının güncellenmesi sağlanır.
        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS $dbTable")
        }

    }


    fun Insert(values:ContentValues):Long{
        val ID= sqlDB!!.insert(dbTable,"",values)
        return ID
    }

    fun  Query(projection:Array<String>,selection:String,selectionArgs:Array<String>,sorOrder:String):Cursor{
        val qb=SQLiteQueryBuilder()
        qb.tables=dbTable
        val cursor=qb.query(sqlDB,projection,selection,selectionArgs,null,null,sorOrder)
        return cursor
    }

    fun Delete(selection:String,selectionArgs:Array<String>):Int{
        val count=sqlDB!!.delete(dbTable,selection,selectionArgs)
        return  count
    }

    fun Update(values:ContentValues,selection:String,selectionargs:Array<String>):Int{
        val count=sqlDB!!.update(dbTable,values,selection,selectionargs)
        return count
    }

}
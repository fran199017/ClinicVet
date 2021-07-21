package com.example.veterinariafinal.Tienda

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.veterinariafinal.AdminSQLiteOpenHelper
import com.example.veterinariafinal.Menu

import com.example.veterinariafinal.databinding.ActivityTiendaBinding

class Tienda : AppCompatActivity() {
    private lateinit var binding: ActivityTiendaBinding
    var itemsTienda:MutableList<DataTienda> = mutableListOf()

    override fun onBackPressed() {
        startActivity(Intent(this, Menu::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityTiendaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCarrito.setOnClickListener(){
            var intent= Intent(this,Carrito::class.java)
            startActivity(intent)
        }
        val db = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
        val cursor = db.readableDatabase.rawQuery("SELECT * from tbltienda order by id", null)

        if (cursor.moveToFirst()) {
            do {
                //Cogemos los datos que necesitamos de la BBDD
                var id:Int=cursor.getInt((cursor.getColumnIndex("id")))
                var img: ByteArray =cursor.getBlob(cursor.getColumnIndex("img"))
                var titulo: String = cursor.getString(cursor.getColumnIndex("titulo"))
                var descripcion: String = cursor.getString(cursor.getColumnIndex("descripcion"))
                var precio: String = cursor.getString(cursor.getColumnIndex("precio"))

                //Los añadimos a nuestra lista de items y a su vez al dataclass (La imagen la convertirmos a bitmap)
                var objeto1= DataTienda(id,convertirIMG(img),titulo,descripcion,precio)
                itemsTienda.add(objeto1)

            } while (cursor.moveToNext())
        }//Cursorfin

        initRecycler()
    }//onCreate

    fun initRecycler() {
        binding.recyclertienda.layoutManager = GridLayoutManager(this, 1)
        val adapter = TiendaAdapter(itemsTienda)
        binding.recyclertienda.adapter = adapter

    }

    //Funcion para devolver la imagen de la base de datos y cargarla en el dataclass
    fun convertirIMG(img: ByteArray): Bitmap {
        var bitmap= BitmapFactory.decodeByteArray(img, 0, img.size)
        return bitmap
    }
}
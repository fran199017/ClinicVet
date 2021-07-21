package com.example.veterinariafinal.Curiosidades

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.veterinariafinal.AdminSQLiteOpenHelper
import com.example.veterinariafinal.databinding.ActivityCuriosidadesBinding
import com.example.veterinariafinal.idUserCompartido

class Curiosidades : AppCompatActivity() {
    private lateinit var binding: ActivityCuriosidadesBinding
    var itemsCuriosidades: MutableList<DataCuriosidades> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCuriosidadesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //iniciamos la db donde comprobaremos cada animal
            val db = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
           // val cursor = db.readableDatabase.rawQuery("SELECT * from tblcuriosidades where especie=$eleccion", null)
            val cursor = db.readableDatabase.rawQuery("SELECT * from tblcuriosidades", null)

            if (cursor.moveToFirst()) {
                do {
                    //Cogemos los datos que necesitamos de la BBDD
                    var id:Int=cursor.getInt((cursor.getColumnIndex("id")))
                    var img: ByteArray =cursor.getBlob(cursor.getColumnIndex("img"))
                    var titulo: String = cursor.getString(cursor.getColumnIndex("titulo"))
                    var especie: String = cursor.getString(cursor.getColumnIndex("especie"))
                    var curiosidad: String = cursor.getString(cursor.getColumnIndex("curiosidad"))

                    //Los añadimos a nuestra lista de items y a su vez al dataclass (La imagen la convertirmos a bitmap)
                    var objeto1= DataCuriosidades(id,titulo, especie,convertirIMG(img),curiosidad)
                    itemsCuriosidades.add(objeto1)

                } while (cursor.moveToNext())
            }//Cursorfin
            initRecycler()
      //  }

    }//onCreate
    fun initRecycler() {
        binding.recyclercuriosidades.layoutManager = GridLayoutManager(this, 1)
        val adapter = CuriosidadesAdapter(itemsCuriosidades)
        binding.recyclercuriosidades.adapter = adapter

    }
    //Funcion para devolver la imagen de la base de datos y cargarla en el dataclass
    fun convertirIMG(img: ByteArray): Bitmap {
        var bitmap= BitmapFactory.decodeByteArray(img, 0, img.size)
        return bitmap
    }
}
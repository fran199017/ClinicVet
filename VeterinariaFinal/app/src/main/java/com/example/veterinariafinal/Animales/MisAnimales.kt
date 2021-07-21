package com.example.veterinariafinal.Animales

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.veterinariafinal.AdminSQLiteOpenHelper
import com.example.veterinariafinal.Menu
import com.example.veterinariafinal.R
import com.example.veterinariafinal.databinding.ActivityMisAnimalesBinding
import com.example.veterinariafinal.idUserCompartido


class MisAnimales : AppCompatActivity() {
    private lateinit var binding: ActivityMisAnimalesBinding
    var itemsAnimales: MutableList<DataAnimales> = mutableListOf()


    override fun onBackPressed() {
        startActivity(Intent(this, Menu::class.java))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        this.setTitle(R.string.misanimales);
        binding = ActivityMisAnimalesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //iniciamos la db donde comprobaremos cada animal
        val db = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
        val cursor = db.readableDatabase.rawQuery("SELECT * from tblanimales where id_usuario=$idUserCompartido ", null)

        if (cursor.moveToFirst()) {
            do {
                //Cogemos los datos que necesitamos de la BBDD
                var id:Int=cursor.getInt((cursor.getColumnIndex("id")))
                var img: ByteArray =cursor.getBlob(cursor.getColumnIndex("img"))
                var nombre: String = cursor.getString(cursor.getColumnIndex("nombre"))
                var chip: String = cursor.getString(cursor.getColumnIndex("chip"))

                //Los añadimos a nuestra lista de items y a su vez al dataclass (La imagen la convertirmos a bitmap)
                var objeto1= DataAnimales(id, convertirIMG(img), chip, nombre)
                itemsAnimales.add(objeto1)

            } while (cursor.moveToNext())
        }//Cursorfin



            //******************REGISTRO ANIMAL**********************
            binding.bRegistroAnimal.setOnClickListener() {
                startActivity(Intent(this, RegistroAnimal::class.java))
            }

            initRecycler()
    }//onCreate

    fun initRecycler() {
        binding.recycleranimales.layoutManager = GridLayoutManager(this, 3)
        val adapter = AnimalAdapter(itemsAnimales)
        binding.recycleranimales.adapter = adapter

    }

    //Funcion para devolver la imagen de la base de datos y cargarla en el dataclass
    fun convertirIMG(img: ByteArray):Bitmap{
        var bitmap= BitmapFactory.decodeByteArray(img, 0, img.size)
        return bitmap
    }


}
package com.example.veterinariafinal

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.veterinariafinal.Animales.MisAnimales
import com.example.veterinariafinal.Cuidados.Cuidados
import com.example.veterinariafinal.Curiosidades.Curiosidades
import com.example.veterinariafinal.Tienda.Tienda
import com.example.veterinariafinal.databinding.ActivityMenuBinding
import com.example.veterinariafinal.pkCitas.RegistroCita

class Menu : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMenuBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.labelBienvenido.setText("Bienvenid@ $nombreUserCompartido")

        //iniciamos la db donde  pondremos la foto del usuario
        val db = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
        // val cursor = db.readableDatabase.rawQuery("SELECT * from tblcuriosidades where especie=$eleccion", null)
        val cursor = db.readableDatabase.rawQuery("SELECT * from tblusuarios where id=$idUserCompartido ", null)
        if (cursor.moveToFirst()) {
            do {
                //Cogemos los datos que necesitamos de la BBDD
                var img: ByteArray =cursor.getBlob(cursor.getColumnIndex("img"))

                //Los añadimos a nuestra lista de items y a su vez al dataclass (La imagen la convertirmos a bitmap)
                binding.imagenUsuario.setImageBitmap(convertirIMG(img))
            } while (cursor.moveToNext())
        }//Cursorfin


        // ****************boton mis animales******************
        binding.bAnimales.setOnClickListener() {    startActivity(Intent(this, MisAnimales::class.java)) }
        // ****************botonCitas******************
        binding.bCita.setOnClickListener(){ startActivity(Intent(this, RegistroCita::class.java))}
        // ****************boton Curiosidades******************
        binding.bCuriosidades.setOnClickListener(){ startActivity(Intent(this, Curiosidades::class.java))}
        // ****************boton Tienda******************
        binding.bTienda.setOnClickListener({startActivity(Intent(this, Tienda::class.java))})
        binding.bCuidados.setOnClickListener({startActivity(Intent(this, Cuidados::class.java))})
    }
    //Funcion para devolver la imagen de la base de datos y cargarla en el dataclass
    fun convertirIMG(img: ByteArray): Bitmap {
        var bitmap= BitmapFactory.decodeByteArray(img, 0, img.size)
        return bitmap
    }
}
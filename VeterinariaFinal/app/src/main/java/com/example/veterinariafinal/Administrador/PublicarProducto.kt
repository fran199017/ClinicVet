package com.example.veterinariafinal.Administrador

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.veterinariafinal.AdminSQLiteOpenHelper
import com.example.veterinariafinal.R
import com.example.veterinariafinal.databinding.ActivityPublicarProductoBinding
import java.io.ByteArrayOutputStream

class PublicarProducto : AppCompatActivity() {
    private lateinit var binding: ActivityPublicarProductoBinding
    var img: ImageView? =null  //variable para cargar la imagen de la galeria.
    var inputData: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityPublicarProductoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //boton publicar
        binding.bRealizarPubli2.setOnClickListener(){    newPublicacion() }
    }//onCreate


    //Funciones
    fun newPublicacion() {
        if (binding.etTitulo2.text.isEmpty()|| binding.etPublicacion2.text.isEmpty()){
            Toast.makeText(this,"Rellene todos los campos", Toast.LENGTH_SHORT).show()
        }else{
            val cursor = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
            val bd = cursor.writableDatabase
            val registro = ContentValues()

            registro.put("titulo", binding.etTitulo2.text.toString());
            registro.put("descripcion", binding.etPublicacion2.text.toString());
            registro.put("precio", binding.textPrecio.text.toString());
            /*Si la IMG esta vacia cogemos una por defecto, la convertirmos a BITMAP y
            posteriormente llamamos al metodo que lo convierte a Bytearray a la BD*/
            if (inputData==null){
                val bitMap:Bitmap= BitmapFactory.decodeResource(resources, R.drawable.animalvacio)
                img?.setImageBitmap(bitMap)
                registro.put("img",bitmapToByteArray(bitMap))
                //Y si no esta vacia la cargamos de la funcion cargarIMG.
            }else
                registro.put("img", inputData)

            bd.insert("tbltienda", null, registro)
            bd.close()
            Toast.makeText(this,"Publicación realizada", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AdministradorMenu::class.java))
        }
    }//fun



    //Funcion convertir Bitmap to Bytearray para almacenar una imagen en la BBDD
    fun bitmapToByteArray(bitmap: Bitmap):ByteArray{
        ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, this)
            return toByteArray()
        }
    }

    fun onclick(view: View) {
        cargarimg()
    }

    private fun cargarimg() {
        //le indicamos de donde vamos a coger la imagen
        var intent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.setType("image/")
        startActivityForResult(android.content.Intent.createChooser(intent, "Seleccione la app"), 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            var path: Uri? = data?.data
            img?.setImageURI(path)

            //La convertimos a Bytearray para almacenarla en la DB
            inputData = path?.let { contentResolver.openInputStream(it)?.readBytes() }
            println(inputData)
        }
    }
}
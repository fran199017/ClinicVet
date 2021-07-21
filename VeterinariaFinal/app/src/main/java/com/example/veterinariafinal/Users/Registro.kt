package com.example.veterinariafinal.Users

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
import com.example.veterinariafinal.MainActivity
import com.example.veterinariafinal.R
import com.example.veterinariafinal.databinding.ActivityRegistroBinding
import java.io.ByteArrayOutputStream


class Registro : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    var img: ImageView? =null  //variable para cargar la imagen de la galeria.
    var inputData: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        img = findViewById(R.id.imgID)

        //Creacion de usuario Administrador
        val cursor = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
        val bd = cursor.writableDatabase
        val registro = ContentValues()

        registro.put("usuario","admin" )     ;registro.put("contraseña","admin")
        registro.put("nombre","admin" )               ;registro.put("apellidos","admin" )
        registro.put("dni","admin" )                     ;registro.put("domicilio","admin")
        registro.put("ciudad","admin" )               ;registro.put("poblacion","admin" )
        registro.put("codigopostal","admin")

        bd.insert("tblusuarios", null, registro)
        bd.close()
        //Registro de un nuevo usuario.
        binding.bValidar.setOnClickListener(){
            newUser()
        }//bValidar

    }//onCreate

    //Escritura de datos en la activity Registro
    fun newUser(){

        if (binding.txtUsuarioRegistro.text.isEmpty() || binding.txtPassword.text.toString().isEmpty() ||  binding.txtApellidos.text.toString().isEmpty()
            || binding.txtNombre.text.toString().isEmpty()||  binding.txtDNI.text.toString().isEmpty()||  binding.txtDomicilio.text.toString().isEmpty()
            || binding.txtCiudad.text.toString().isEmpty() ||  binding.txtPassword.text.toString().isEmpty() ||  binding.txtCP.text.toString().isEmpty()){

                Toast.makeText(this,"Rellene todos los campos",Toast.LENGTH_SHORT).show()
        }else{
            val cursor = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
            val bd = cursor.writableDatabase
            val registro = ContentValues()

            registro.put("usuario", binding.txtUsuarioRegistro.text.toString())     ;registro.put("contraseña",binding.txtPassword.text.toString() )
            registro.put("nombre",binding.txtNombre.text.toString() )               ;registro.put("apellidos",binding.txtApellidos.text.toString() )
            registro.put("dni",binding.txtDNI.text.toString() )                     ;registro.put("domicilio",binding.txtDomicilio.text.toString() )
            registro.put("ciudad",binding.txtCiudad.text.toString() )               ;registro.put("poblacion",binding.txtPoblacion.text.toString() )
            registro.put("codigopostal",binding.txtCP.text.toString() )

            /*Si la IMG esta vacia cogemos una por defecto, la convertirmos a BITMAP y
                    posteriormente llamamos al metodo que lo convierte a Bytearray a la BD*/
            if (inputData==null){
                val bitMap:Bitmap= BitmapFactory.decodeResource(resources, R.drawable.person)
                img?.setImageBitmap(bitMap)
                registro.put("img",bitmapToByteArray(bitMap))
                //Y si no esta vacia la cargamos de la funcion cargarIMG.
            }else
                registro.put("img", inputData)

            bd.insert("tblusuarios", null, registro)
            bd.close()

            //Borrar campos
            binding.txtUsuarioRegistro.setText("")  ;binding.txtPassword.setText("")        ;binding.txtNombre.setText("")
            binding.txtApellidos.setText("")        ;binding.txtTelefono.setText("")        ;binding.txtDNI.setText("")
            binding.txtDomicilio.setText("")        ;binding.txtCiudad.setText("")          ;binding.txtPoblacion.setText("")   ;binding.txtCP.setText("");
            binding.imgID2.setImageURI(null)
            Toast.makeText(this,"Nuevo Usuario registrado",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))

        }


    }
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
        if(resultCode== RESULT_OK){
            var path: Uri?=data?.data
            img?.setImageURI(path)

            //La convertimos a Bytearray para almacenarla en la DB
            inputData = path?.let { contentResolver.openInputStream(it)?.readBytes() }
            println(inputData)
        }

    }
}
package com.example.veterinariafinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.veterinariafinal.Administrador.AdministradorMenu
import com.example.veterinariafinal.Users.DeleteUser
import com.example.veterinariafinal.Users.Registro
import com.example.veterinariafinal.databinding.ActivityMainBinding

//DATOS COMPARTIDOS EN TODA LA APP
var idUserCompartido:Int=0
var nombreUserCompartido:String=""
var flagAdmin:Boolean=false

//************************************

class MainActivity : AppCompatActivity() {
    var titulo:String="main"
    private lateinit var binding: ActivityMainBinding
    var flagAcceder:Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        this.setTitle(R.string.main);

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val admin = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
        val bd = admin.writableDatabase

        //*************Boton Login***********
        binding.bAcceder.setOnClickListener() {
            var intent= Intent(this,Menu::class.java)
            comprobarLogin()
            //Si comprobarLogin es correcto y flag es true iniciamos la actividad y volvemos a ponerlo false para volver a comprobar si volvemos atrás.
            if (flagAcceder==true && flagAdmin==true){
                startActivity(Intent(this, AdministradorMenu::class.java))
                Toast.makeText(this,"Bienvenido Administrador",Toast.LENGTH_SHORT).show()
                flagAdmin==false
            }


            else if(flagAcceder==true){
                startActivity(intent)
                Toast.makeText(this,"Usuario y contraseña correctos",Toast.LENGTH_SHORT).show()

            }
            else
                Toast.makeText(this,"Usuario y/o contraseña incorrectos",Toast.LENGTH_SHORT).show()

        }

        //************Boton registro********
        binding.bRegistro.setOnClickListener() {
            startActivity(Intent(this, Registro::class.java))
        }

        //************Boton delete user********
        binding.bDeleteUser.setOnClickListener(){
            startActivity(Intent(this, DeleteUser::class.java))
        }
    }//onCreate

    /* *********************Funciones********************* */

    fun comprobarLogin(){

        val db = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
        //val cursor= db.readableDatabase.query("tblusuarios", arrayOf("usuario", "contraseña"), null, null, null, null, null)
        val cursor= db.readableDatabase.rawQuery("SELECT * from tblusuarios ",null)
        if (cursor.moveToFirst()){
            do {
                var id:Int= cursor.getInt(cursor.getColumnIndex("id"))
                var user: String =cursor.getString(cursor.getColumnIndex("usuario"))
                var password:String= cursor.getString(cursor.getColumnIndex("contraseña"))
                println("id "+ id+", ")
                println("Nombre usuario "+user+", ")
                println("Contraseña "+password+", ")

                //hacemos la comprobacion del usuario y contraseña con las cajas de texto y el flag para iniciar la actividad a True si es correcto.
                if(user==binding.txtUsuario.text.toString() && password==binding.txtContrasena.text.toString()
                    && binding.txtUsuario.text.toString()=="admin" && binding.txtContrasena.text.toString()=="admin") {
                    flagAcceder = true
                    flagAdmin = true

                }else if (user==binding.txtUsuario.text.toString() && password==binding.txtContrasena.text.toString()){
                    flagAcceder=true
                    idUserCompartido= cursor.getInt(cursor.getColumnIndex("id"))
                    nombreUserCompartido=user
                }
            }while (cursor.moveToNext())
            cursor.close()
            db.close()
        }

    }
}//activity
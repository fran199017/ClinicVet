package com.example.veterinariafinal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory

class AdminSQLiteOpenHelper(context: Context, name: String, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table tblusuarios(id integer primary key autoincrement, img blob, usuario text not null, contraseña text not null, nombre text not null, apellidos text not null, dni text not null , domicilio text not null, ciudad text not null, poblacion text not null, codigopostal int, UNIQUE (id) ON CONFLICT FAIL)")
        db.execSQL("create table tblanimales(id integer primary key autoincrement,img blob, chip text not null, nombre text not null, nacimiento text not null, sexo text not null, especie text not null , raza text not null, id_usuario integer, propietario text,foreign key(propietario) references tblusuarios(usuario), foreign key(id_usuario) references tblusuarios(id),UNIQUE (id) ON CONFLICT FAIL)")
        db.execSQL("create table tbladopcion(id integer primary key autoincrement, chip text, nombre text not null, nacimiento text not null, sexo text, especie text not null , raza text not null, caracter text not null, observaciones text, UNIQUE (id) ON CONFLICT FAIL)")
        db.execSQL("create table tblcuriosidades(id integer primary key autoincrement, titulo text not null, especie text not null,img blob, curiosidad text not null)")
        db.execSQL("create table tblcitas(id integer primary key autoincrement, fecha text not null, hora text not null,doctor text not null, motivo text not null, mascota not null,id_usuario integer, foreign key(id_usuario) references tblusuarios(id), UNIQUE (id) ON CONFLICT FAIL)")
        db.execSQL("create table tbltienda(id integer primary key autoincrement, img blob, titulo text not null, descripcion text not null, precio text not null, UNIQUE (id) ON CONFLICT FAIL)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists tblusuarios")
        db.execSQL("drop table if exists tblanimales")
        db.execSQL("drop table if exists tbladopcion")
        db.execSQL("drop table if exists tblcitas")
        db.execSQL("drop table if exists tbltienda")
        db.execSQL("drop table if exists tblcuriosidades")
        onCreate(db)
    }
}
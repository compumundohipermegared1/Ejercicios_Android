package com.example.listaelementos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.midi.MidiManager;
import android.util.Log;

import com.example.listaelementos.models.Contacto;

import java.util.ArrayList;
import java.util.List;

public class ContactoDataSource {

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;
    String TAG = "DB: ";

    public ContactoDataSource (Context context){
        dbhelper = new ContactoDbOpenHelper(context);
    }

    public void openDB(){
        db = dbhelper.getWritableDatabase();
        Log.i(TAG,"openDB");
    }

    public void closeDB(){
        dbhelper.close();
        Log.i(TAG,"closeDB");
    }

    public List<Contacto> obtenerContactos(){
        List<Contacto> contactos = new ArrayList<>();
        String query = "SELECT * FROM contacto ";
        Cursor cursor = db.rawQuery(query, null);

        Log.i (TAG," Filas retornadas" + cursor.getCount());
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                Contacto contacto = new Contacto();

                contacto.setId(cursor.getLong(0));
                contacto.setNombre(cursor.getString(1));
                contacto.setPaterno(cursor.getString(2));
                contacto.setMaterno(cursor.getString(3));
                contacto.setTelefono(cursor.getString(4));

                contactos.add(contacto);
            }
        }
        return contactos;
    }

    public Contacto insertarContacto(Contacto contacto){
        ContentValues valores = new ContentValues();
        valores.put("nombre",contacto.getNombre());
        valores.put("apellido_p",contacto.getPaterno());
        valores.put("apellido_m",contacto.getMaterno());
        valores.put("telefono",contacto.getTelefono());

        long insertId = db.insert("contacto", null, valores);
        contacto.setId(insertId);

        return contacto;
    }

    public Boolean eliminarContacto(Long id){
        String where = "id = "+ id;
        int result = db.delete("contacto", where, null);
        return (result == 1);
    }
}

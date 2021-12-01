package com.example.listaelementos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listaelementos.adapters.ContactoAdapter;
import com.example.listaelementos.db.ContactoDataSource;
import com.example.listaelementos.models.Contacto;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final int REQUEST_CODE_AGREGAR_CONTACTO = 1001;
    public static final int REQUEST_CODE_DETALLE_ACTIVITY = 1002;
    ListView lvContactos;
    List<Contacto> contactos;
    ContactoDataSource dataSource;
    ArrayAdapter<Contacto> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Agenda");

        lvContactos = findViewById(R.id.lvContactos);

        dataSource = new ContactoDataSource(this);

        dataSource.openDB();
        contactos = dataSource.obtenerContactos();
        dataSource.closeDB();

        adapter = new ContactoAdapter(this, R.layout.contato_item, contactos);

        lvContactos.setAdapter(adapter);
        lvContactos.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        Contacto contacto = contactos.get(i);
        String nombre = contacto.getNombre();
        Log.i("MainActivity", "nombre: "+ nombre);
        Toast.makeText(this,"Click en item "+i, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, DetalleActivity.class);
        intent.putExtra("nombre", nombre);
        intent.putExtra("contacto", contacto);

        startActivityForResult(intent, REQUEST_CODE_DETALLE_ACTIVITY);
    }

    /*@Override
    public boolean onCreateOptionMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }
    */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        switch (id){

            case R.id.action_guardar_contacto:
                Intent intent = new Intent(this, AgregarContactoActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_AGREGAR_CONTACTO);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_AGREGAR_CONTACTO && resultCode == 1){

            Log.i("MainActivity", "actualizar el listaview");
            actualizarContacto();

        }
    }

    public void actualizarContacto(){
        dataSource.openDB();
        contactos = dataSource.obtenerContactos();
        dataSource.closeDB();

        adapter.clear();
        adapter.addAll(contactos);
        adapter.notifyDataSetChanged();

    }
}
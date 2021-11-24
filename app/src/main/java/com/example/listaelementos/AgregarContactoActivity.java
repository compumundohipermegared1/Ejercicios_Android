package com.example.listaelementos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listaelementos.models.Contacto;

public class AgregarContactoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       // setContentView(R.layout);
        setContentView(R.layout.activity_agregar_contacto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_agregar_contacto_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_guardar_contacto:
                Intent intent = new Intent(this, AgregarContactoActivity.class);
                //Toast.makeText(this, "Guardar", Toast.LENGTH_SHORT).show();
                dataSource.openDB();
                guardarContacto();
                dataSource.closeDB();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void guardarContacto(){
        String nombre = etNombre.getText().toString();
        String paterno = etpaterno.getText().toString();
        String materno = etmaterno.getText().toString();
        String telefono = ettelefono.getText().toString();

        if(crearContacto(nombre, paterno, materno, telefono) != -1){
            Toast.makeText(this, "contacto agregado", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.i("ContactActivity","error");
        }

    }

    public long crearContacto(String nombre, String paterno, String materno, String telefono){
        Contacto contacto = new Contacto();
        contacto.setNombre(nombre);
        contacto.setPaterno(paterno);
        contacto.setMaterno(materno);
        contacto.setTelefono(telefono);

        contacto = dataSource.insertarContacto(contacto);

        return contacto.getId();
    }
}

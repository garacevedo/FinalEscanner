package com.example.app_escaner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_escaner.DB.ConexionPG;
import com.example.app_escaner.entidades.Ciudadanos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MostrarCiudadanos extends AppCompatActivity {
    EditText txtIdentificacion, txtNombres, txtApellidos, txtFechaNacimiento, txtRh, txtGrupoSanguineo, txtSexo, txtRequerido;
    Button btnGuardarActualziacion;
    FloatingActionButton fabEditar, fabEliminar;

    Ciudadanos ciudadanos = new Ciudadanos();
    int id = 0;
    ConexionPG bd = new ConexionPG();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_ciudadanos);

        txtIdentificacion = findViewById(R.id.txtIdentificacion);
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtRh = findViewById(R.id.txtRh);
        txtGrupoSanguineo = findViewById(R.id.txtGrupoSanguineo);
        txtSexo = findViewById(R.id.txtSexo);
        txtRequerido = findViewById(R.id.txtRequerido);
        btnGuardarActualziacion = findViewById(R.id.btnGuardarActualziacion);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        bd.Database();
        ciudadanos = bd.verCiudadanos(id);
        System.out.println("Ver ciudadanos consultados con id: " + id);
        System.out.println(ciudadanos.toString());

        if (ciudadanos != null) {
            txtIdentificacion.setText(String.valueOf(ciudadanos.getNumero_identificacion()));
            txtNombres.setText(ciudadanos.getNombre());
            txtApellidos.setText(ciudadanos.getApellidos());
            txtFechaNacimiento.setText(ciudadanos.getFecha_nacimento());
            txtRh.setText(ciudadanos.getRh());
            txtGrupoSanguineo.setText(ciudadanos.getGrupo_sanguineo());
            txtSexo.setText(ciudadanos.getSexo());
            txtRequerido.setText(ciudadanos.getRequerido());
            btnGuardarActualziacion.setVisibility(View.INVISIBLE);


            txtIdentificacion.setInputType(InputType.TYPE_NULL);
            txtNombres.setInputType(InputType.TYPE_NULL);
            txtApellidos.setInputType(InputType.TYPE_NULL);
            txtFechaNacimiento.setInputType(InputType.TYPE_NULL);
            txtRh.setInputType(InputType.TYPE_NULL);
            txtGrupoSanguineo.setInputType(InputType.TYPE_NULL);
            txtSexo.setInputType(InputType.TYPE_NULL);
            txtRequerido.setInputType(InputType.TYPE_NULL);

        }
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MostrarCiudadanos.this, EditarCiudadanos.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MostrarCiudadanos.this);
                builder.setMessage("Está seguro de eliminar el ciudadano?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String message = bd.eliminarCiudadano(id);
                                regresarMain();
                                Toast.makeText(MostrarCiudadanos.this, message, Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }

    private void regresarMain(){
        Intent intent = new Intent(this, MostrarCiudadanos.class);
        startActivity(intent);
    }
}
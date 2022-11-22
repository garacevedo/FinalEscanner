package com.example.app_escaner;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_escaner.DB.ConexionPG;
import com.example.app_escaner.entidades.Ciudadanos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarCiudadanos extends AppCompatActivity {
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

        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar.setVisibility(View.INVISIBLE);

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

            btnGuardarActualziacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!txtIdentificacion.getText().toString().equals("")) {
                        String message = bd.actualizarCiudadano(id, Integer.parseInt(txtIdentificacion.getText().toString()), txtNombres.getText().toString(),
                                txtApellidos.getText().toString(), txtFechaNacimiento.getText().toString(), txtRh.getText().toString().charAt(0),
                                txtGrupoSanguineo.getText().toString().charAt(0), txtSexo.getText().toString(), txtRequerido.getText().toString());
                        Toast.makeText(EditarCiudadanos.this, message, Toast.LENGTH_LONG).show();
                        mostrarCiudadanos();
                    }
                }
            });

        }

    }

    public void mostrarCiudadanos(){
        Intent intent = new Intent(this, MostrarCiudadanos.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}
package com.example.app_escaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void IrInicar(View ver){
        Intent i= new Intent(this, IniciarPantallaActivity.class);
        startActivity(i);

    }
    public void IrRegistrarse(View view){
        Intent i= new Intent(this,RegistrarseActivity.class);
        startActivity(i);


    }
}
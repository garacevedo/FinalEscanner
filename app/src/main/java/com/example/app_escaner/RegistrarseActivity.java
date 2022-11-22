package com.example.app_escaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrarseActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    // Initialize Firebase Auth
    private EditText Nombre;
    private EditText Apellido;
    private EditText Fuerzapublica;
    private EditText Rango;
    private EditText IDFuerza;
    private EditText correo;
    private EditText contrasena;
    private EditText confirmarcontrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);


        mAuth = FirebaseAuth.getInstance();

        Nombre = findViewById(R.id.Nombre);
        Apellido = findViewById(R.id.Apellido);
        Fuerzapublica = findViewById(R.id.FuerzaPublica);
        Rango = findViewById(R.id.Rango);
        IDFuerza = findViewById(R.id.IDFuerza);
        correo = findViewById(R.id.Correo1);
        contrasena = findViewById(R.id.contrasena);
        confirmarcontrasena = findViewById(R.id.Confirmarcontrasena);


    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }


    public void registrarUsuario(View view){

        if(contrasena.getText().toString().equals(confirmarcontrasena.getText().toString())){

            mAuth.createUserWithEmailAndPassword(correo.getText().toString(),contrasena.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Se registro correctamente",
                                        Toast.LENGTH_SHORT).show();
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });






        }else{

            Toast.makeText(this, "Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
        }




    }

}
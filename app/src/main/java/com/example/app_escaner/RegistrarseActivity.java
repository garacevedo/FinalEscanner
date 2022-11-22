package com.example.app_escaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_escaner.DB.ConexionPG;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrarseActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static ConexionPG bd = new ConexionPG();

    // Initialize Firebase Auth
    EditText txtIdentificacionFuerza, txtNombreFuerza, txtApellidoFuerza, txtFuerzaPublica, txtRango, txtIdFuerza, txtCorreo1;
    Button btnRegistarFuerza, btnRecordarClave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);


        mAuth = FirebaseAuth.getInstance();

        txtIdentificacionFuerza = findViewById(R.id.txtIdentificacionFuerza);
        txtNombreFuerza = findViewById(R.id.txtNombreFuerza);
        txtApellidoFuerza = findViewById(R.id.txtApellidoFuerza);
        txtFuerzaPublica = findViewById(R.id.txtFuerzaPublica);
        txtRango = findViewById(R.id.txtRango);
        txtIdFuerza = findViewById(R.id.txtIdFuerza);
        txtCorreo1 = findViewById(R.id.txtCorreo1);
        btnRegistarFuerza = findViewById(R.id.btnRegistarFuerza);
        btnRecordarClave = findViewById(R.id.btnRecordarClave);

        btnRegistarFuerza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bd.Database();
                String message = bd.insertarFuerzaPublica(Integer.parseInt(txtIdentificacionFuerza.getText().toString()), txtNombreFuerza.getText().toString(),
                        txtApellidoFuerza.getText().toString(),txtFuerzaPublica.getText().toString(),
                        txtRango.getText().toString(),Integer.parseInt(txtIdFuerza.getText().toString()),txtCorreo1.getText().toString());
                if (!message.contains("Falla")){
                    Toast.makeText(RegistrarseActivity.this, message, Toast.LENGTH_LONG).show();
                    limpiarCamposRegistro();
                    mostrarInicio();
                }else {
                    Toast.makeText(RegistrarseActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void limpiarCamposRegistro(){
        txtIdentificacionFuerza.setText("");
        txtNombreFuerza.setText("");
        txtApellidoFuerza.setText("");
        txtFuerzaPublica.setText("");
        txtRango.setText("");
        txtIdFuerza.setText("");
        txtCorreo1.setText("");
        btnRegistarFuerza.setText("");
        btnRecordarClave.setText("");
    }

    public void mostrarInicio(){
        Intent intent = new Intent(this, Inicio.class);
        startActivity(intent);
    }

    /*
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




    }*/

}
package com.example.app_escaner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_escaner.DB.ConexionPG;
import com.example.app_escaner.entidades.FuerzaPublica;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class IniciarPantallaActivity extends AppCompatActivity {

    private EditText txtCorreo;
    private EditText contrasena;
    private FirebaseAuth mAuth;
    Button btnRecordarClave;
    public static ConexionPG bd = new ConexionPG();
    FuerzaPublica fuerzaPublica = new FuerzaPublica();
// ...
// Initialize Firebase Auth


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_iniciar_pantalla);

            txtCorreo = findViewById(R.id.txtCorreo);
            contrasena = findViewById(R.id.contrasena);
            mAuth = FirebaseAuth.getInstance();
            btnRecordarClave = findViewById(R.id.btnRecordarClave);

            btnRecordarClave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!txtCorreo.getText().toString().equals("")) {
                        bd.Database();
                        fuerzaPublica = bd.consultaClaveFuerzaPublica(txtCorreo.getText().toString().trim());
                        String from = "alfonso083.santi@gmail.com";
                        String pass = "picqdysxscgztrui";
                        System.out.println("correeeeeeeeeeeeo");
                        System.out.println("w" + txtCorreo.getText().toString() + "e");
                        String[] to = {txtCorreo.getText().toString().trim()}; // list of recipient email addresses
                        String subject = "Recordar Contraseña App_Escaner";
                        String body = "Hola Estimad@ " + fuerzaPublica.getNombre() + ",\n" +
                                "No te olvides de la contraseña de la aplicación App_Escaner:\n\n" +
                                fuerzaPublica.getClave() + "\n" +
                                "\nAtentamente,\n" +
                                "Equipo de App_Escaner.";
                        sendFromGMail(from, pass, to, subject, body);
                        Toast.makeText(IniciarPantallaActivity.this, "Enviando correo con la contraseña...", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(IniciarPantallaActivity.this, "Debe ingresar pro lo menos el correo para habilitar esta opción.", Toast.LENGTH_LONG).show();
                    }

                }
            });
    }


    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";

        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");//587, 465
        props.put("mail.smtp.auth", "true");


        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {


            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }


            message.setSubject(subject);
            message.setText(body);


            Transport transport = session.getTransport("smtp");


            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void IniciarSesion(View view) {


        mAuth.signInWithEmailAndPassword(txtCorreo.getText().toString().trim(), contrasena.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Se inicio Correctamente la sesión",
                                    Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(getApplicationContext(),Inicio.class);
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

    }
}
package com.example.app_escaner;

import android.content.Intent;
import android.net.Uri;
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

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RegistrarseActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static ConexionPG bd = new ConexionPG();

    // Initialize Firebase Auth
    EditText txtIdentificacionFuerza, txtNombreFuerza, txtApellidoFuerza, txtFuerzaPublica, txtRango, txtIdFuerza, txtCorreo1;
    Button btnRegistarFuerza;


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


        btnRegistarFuerza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bd.Database();
                String message[] = bd.insertarFuerzaPublica(Integer.parseInt(txtIdentificacionFuerza.getText().toString()), txtNombreFuerza.getText().toString(),
                        txtApellidoFuerza.getText().toString(),txtFuerzaPublica.getText().toString(),
                        txtRango.getText().toString(),Integer.parseInt(txtIdFuerza.getText().toString()),txtCorreo1.getText().toString());
                System.out.println(txtCorreo1.getText().toString());
                if (!message[0].contains("Falla")){
                    Toast.makeText(RegistrarseActivity.this, message[0], Toast.LENGTH_LONG).show();


                    String from = "alfonso083.santi@gmail.com";
                    String pass = "picqdysxscgztrui";
                    System.out.println("correeeeeeeeeeeeo");
                    System.out.println("w"+txtCorreo1.getText().toString()+"e");
                    String[] to = { txtCorreo1.getText().toString().trim() }; // list of recipient email addresses
                    String subject = "Contraseña App_Escaner de Fuerza Pública";
                    String body = "Hola Estimad@ "+ txtNombreFuerza.getText().toString().trim()+ ",\n" +
                            "Muchas gracias por registrarte al la aplicación App_Escaner.\n\n" +
                            "Enviamos tú contraseña para iniciar sesión de ahora en adelante: " + message[1] +"\n" +
                            "Si en algún momento no recuerdas la contraseña, puedes dar clic al botón de RECORDAR CLAVE al momento de iniciar sesión y te llegará un correo electrónico con la contraseña.\n" +
                            "\n\nAtentamente,\n" +
                            "Equipo de App_Escaner.";
                    sendFromGMail(from, pass, to, subject, body);

                    //sendMail(message[1]);
                    limpiarCamposRegistro();
                    mostrarInicio();


                }else {
                    Toast.makeText(RegistrarseActivity.this, message[0], Toast.LENGTH_LONG).show();
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
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }



            message.setSubject(subject);
            message.setText(body);


            Transport transport = session.getTransport("smtp");


            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    private void sendMail(String clave){

        String email = txtCorreo1.getText().toString().trim();
        String asunto = "CONTRASEÑA NUEVA";
        String contenido = "Hola estimado usuario!\n" +
                        "Esta es tu contraseña de acceso: " + clave ;

        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, asunto, contenido);
        javaMailAPI.execute();
    }

    private void limpiarCamposRegistro(){
        txtIdentificacionFuerza.setText("");
        txtNombreFuerza.setText("");
        txtApellidoFuerza.setText("");
        txtFuerzaPublica.setText("");
        txtRango.setText("");
        txtIdFuerza.setText("");
        txtCorreo1.setText("");
        //btnRegistarFuerza.setText("");
        //btnRecordarClave.setText("");
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

                        /*Properties props = new Properties();

                    //Configuring properties for gmail
                    //If you are not using gmail you may need to change the values
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.port", "465");
                    Session mSession = Session.getDefaultInstance(props,
                            new javax.mail.Authenticator() {
                                //Authenticating the password
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username, password);
                                }
                            });
                    try {
                        //Creating MimeMessage object
                        Message mm = new MimeMessage(mSession);

                        //Setting sender address
                        mm.setFrom(new InternetAddress(username));
                        //Adding receiver
                        mm.setRecipient(Message.RecipientType.TO, InternetAddress.parse("gabrielandres.angel@gmail.com"));
                        //Adding subject
                        mm.setSubject("mSubject");
                        //Adding message
                        mm.setText("mMessage");
                        //Sending email
                        Transport.send(mm);

                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }*/

}

// picqdysxscgztrui
package com.example.app_escaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_escaner.DB.ConexionPG;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.nio.charset.*;
import java.util.*;


public class Inicio extends AppCompatActivity {

    Button btnScan;
    EditText txtResultado;
    Button btnConsultarCiudadanos,btn_cerrar;
    public static ConexionPG bd = new ConexionPG();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btnScan = findViewById(R.id.btnScan);
        txtResultado = findViewById(R.id.txtResultado);
        btn_cerrar = findViewById(R.id.btn_cerrar);
        btnConsultarCiudadanos = findViewById(R.id.btnConsultarCiudadanos);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(Inicio.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Lector de códigos");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });

        btnConsultarCiudadanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnConsultarCiudadanos = new Intent(Inicio.this, ConsultaCiudadano.class);
                startActivity(btnConsultarCiudadanos);
            }
        });
        btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Se cerro sesion correctamente",
                        Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent btn_cerrar = new Intent(Inicio.this, MainActivity.class);
                startActivity(btn_cerrar);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                //txtResultado.setText(result.getContents());

                System.out.println(result.getContents());
                System.out.println(result.toString());
                System.out.println("******************************************************");
                System.out.println("******************************************************");
                System.out.println("******************************************************");
                System.out.println("******************************************************");
                System.out.println((Object) result.getContents().getClass().getSimpleName());

                byte[] b = result.getContents().getBytes(Charset.forName("UTF-8"));
                ArrayList<String> list3 = new ArrayList<>();
                List<Byte> arraysBytes = new ArrayList<Byte>();
                for (byte i : b) {

                    if (i == 0) {
                        byte[] result1 = new byte[arraysBytes.size()];
                        for (int j = 0; j < arraysBytes.size(); j++) {
                            result1[j] = arraysBytes.get(j).byteValue();
                        }
                        String str1 = new String(result1, StandardCharsets.UTF_8);
                        list3.add(str1);
                        arraysBytes.clear();
                    } else {
                        arraysBytes.add(i);
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    list3.removeIf(Objects::isNull);
                    list3.removeIf(String::isEmpty);
                }


                for (String i : list3) {
                    System.out.println("esooooooooooooo2 " + i);
                }
                System.out.println("******************************************************");
                System.out.println("******************************************************");
                System.out.println("******************************************************");
                String scanContent = (String) result.getContents();
                String scanFormat = (String) result.getFormatName();
                if (scanContent.indexOf("PubDSK_") != -1) {
                    int contador = 0;
                    for (int i = 0; i < scanContent.indexOf("PubDSK_"); i++) {
                        if (Character.isDigit(scanContent.charAt(i)) || Character.isAlphabetic(scanContent.charAt(i))) {
                            if (contador == 0 && (scanContent.charAt(i) == 'I' || scanContent.charAt(i) == 'i')) {
                                i = scanContent.indexOf("PubDSK_");
                                contador = 0;
                            } else {
                                contador = contador + 1;
                            }
                        }
                    }

                    String[] Filtro1 = scanContent.split("PubDSK_");
                    scanContent = Filtro1[1];
                    String[] Filtro2 = scanContent.split("[a-zA-Z]");
                    scanContent = Filtro2[0];
                    if (scanFormat.equals("PDF_417")) {
                        System.out.println("--------------------------------------------------------------------------");
                        System.out.println("--------------------------------------------------------------------------");
                        System.out.println("--------------------------------------------------------------------------");
                        System.out.println(Arrays.toString(Filtro1));
                        System.out.println(Arrays.toString(Filtro2));
                        System.out.println(scanContent);
                        boolean res;
                        if (contador == 10) {
                            res = verificarDocumentoIdentidad(scanContent.substring(17, scanContent.length()));
                            if (res) {
                                txtResultado.setText("El ciudadadano : " + scanContent.substring(17, scanContent.length()) + " No es requerido.");
                                Toast.makeText(this, "Ver en CONSUTLAR CIUDADANOS PARA VERIFICAR.", Toast.LENGTH_LONG).show();
                                processDataFromScanner(list3, scanContent.substring(17, scanContent.length()).trim());
                            } else {
                                txtResultado.setText("El ciduadano ya fue consultado anteriormente: " + scanContent.substring(17, scanContent.length()));
                            }
                        } else {
                            if (contador == 9) {
                                res = verificarDocumentoIdentidad(scanContent.substring(17, scanContent.length()));
                                if (res) {
                                    txtResultado.setText("El ciudadadano : " + scanContent.substring(19, scanContent.length()) + " No es requerido.");
                                    processDataFromScanner(list3, scanContent.substring(19, scanContent.length()).trim());
                                    Toast.makeText(this, "Ver en CONSUTLAR CIUDADANOS PARA VERIFICAR.", Toast.LENGTH_LONG).show();
                                } else {
                                    txtResultado.setText("El ciduadano ya fue consultado anteriormente: " + scanContent.substring(17, scanContent.length()));
                                }
                            } else {
                                if (contador == 0) {
                                    res = verificarDocumentoIdentidad(scanContent.substring(17, scanContent.length()));
                                    if (res) {
                                        txtResultado.setText("El ciudadadano : " + scanContent.substring(17, scanContent.length()) + " No es requerido.");
                                        processDataFromScanner(list3, scanContent.substring(17, scanContent.length()).trim());
                                        Toast.makeText(this, "Ver en CONSUTLAR CIUDADANOS PARA VERIFICAR.", Toast.LENGTH_LONG).show();
                                    } else {
                                        txtResultado.setText("El ciduadano ya fue consultado anteriormente: " + scanContent.substring(17, scanContent.length()));
                                    }
                                } else {
                                    txtResultado.setText("La cédula" + Integer.toString(contador) + ": No se pudo detectar Correctamente. " + scanContent);
                                }
                            }
                        }
                    } else {
                        txtResultado.setText("");
                    }
                } else {
                    txtResultado.setText("Lo sentimos pero el formato PDF_417 no pertenece a una cedula de ciudadania colombiana");
                }
            }
            System.out.println("******************************************************");
            System.out.println("******************************************************");
            System.out.println("******************************************************");


        }


    }

    public boolean verificarDocumentoIdentidad(String documento_identidad) {
        bd.Database();
        boolean res = bd.consultaIdentidadCiudadanos(Integer.parseInt(documento_identidad));
        return res;
    }

    public void processDataFromScanner(ArrayList<String> list, String documento_identidad) {

        bd.Database();
        System.out.println("qqqqqqqqqqqqqqqqqqqqq " + documento_identidad.trim());

        int numero_identificacion = Integer.parseInt(documento_identidad);
        String nombres = "";
        String apellidos = "";
        String fechaNacimento = "";
        char rh = ' ';
        char grupoSanguineo = ' ';
        String sexo = "";
        String requerido = "no";
        String total[] = new String[10];
        int cont = 0;
        for (String i : list) {

            if ((i.charAt(0) == '0') && ((i.charAt(1) == 'F') || (i.charAt(1) == 'M'))) {
                sexo = i.charAt(1) == 'F' ? "Femenino" : "Masculino";
                String sexoRespaldo = i.charAt(1) == 'F' ? "F" : "M";
                rh = i.charAt(i.length() - 1);
                grupoSanguineo = i.charAt(i.length() - 2);
                String fechaNacimento_pre = i.substring(i.indexOf(sexoRespaldo) + 1, i.indexOf(sexoRespaldo) + 9);
                fechaNacimento = fechaNacimento_pre.substring(0, 4) + "-" + fechaNacimento_pre.substring(4, 6) + "-" + fechaNacimento_pre.substring(6);

            }
            String pal = "";

            if (i.contains(documento_identidad) && Character.isLetter(i.charAt(i.length() - 1))) {

                for (int j = 0; j <= i.length() - 1; j++) {
                    if (Character.isLetter(i.charAt(j))) {
                        pal += i.charAt(j);
                    }
                }
                total[0] = pal;
                cont++;
            }
            if (Character.isLetter(i.charAt(0)) && !i.contains("Pub") && i.length() <= 20 && Character.isLetter(i.charAt(2))) {
                total[cont] = i;
                cont++;
            }
        }
        apellidos += total[0] + " " + total[1];
        for (int i = 2; i <= total.length - 1; i++) {
            if (total[i] != null) {
                nombres += total[i] + " ";
            }
        }
        nombres = nombres.trim();
        System.out.println("Listaaaaaaaaaaaaaaaaaaaaa " + Arrays.toString(total));
        System.out.println(numero_identificacion);
        System.out.println(apellidos);
        System.out.println(nombres);
        System.out.println(fechaNacimento);
        System.out.println(grupoSanguineo);
        System.out.println(rh);
        System.out.println(sexo);
        System.out.println(requerido);

        String message = bd.insertarCiudadano(numero_identificacion, nombres, apellidos, fechaNacimento, grupoSanguineo, rh, sexo, requerido);
        //Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }
}
//I335367163����������������������������PubDSK_1����������������114675421023163677��RODRIGUEZ����������������������������ACEVEDO��������������������������������MIGUEL����������������������������������ANGEL������������������������������������0M20051217150010A-��26��nOÿ^T7i:^E/y3k]"e%RXs{kæwÂuuuv³}Ô½^ÃZã}qso¿räimà`oz^cS(ibiq¢l¥w®¸iÇ×À^Îs¸l¸yOVÜ[_]KaEg)ö¿$ÝíRéí����������������������������������������������������������7<��}JÿST¦Vdid¬g~dÖh=w}eËkÚ¡ßm9 ÓØlßÅÇºyË©fºw°}4B3pjl{l}r|iÔr}Ütg©pe&]{ª)a.{3gRe[EYhp¥[q\n­kcàºä´½{JFÕ¿Qú��������������������������������QIa`wÒqo´¢Ü{ô÷ä·cL±;+½%&¢8mÙ*[ eeeeeeeeeeeeeeeeeeeeeeee
//0371861193����������������������������PubDSK_1����������������850752771000706952RODRIGUEZ����������������������������ACEVEDO��������������������������������GABRIEL��������������������������������ANDRES����������������������������������0M20020824150010A+��25��*]ÿp®{[;/,vµ·®W©¯±¬Ey£QTNXEL£¸nnxf0r1~ a¼#j<­_G_ d·j(k©´ J[aOqL[s]c\pyRhb|Xdjn£ü·oÙþ]'ÒFbò��������������������������������������������������������������7;��5\ÿ~{­yt¢y¬m¬^H¢³{ÉÎ:,À`3i´fHeQf®k²V®Tec¬fcUTM ¯®ª¦£¥±©Ñ¯²µ¦n»Î¢Ù3]g?|vypsYxRÉZê°ºrËRe[ÿ������������������������������������&B¡z3yõ>[E\.øËö óÌ»òõù94ã±C eeeeeeeeeeeeeeeeeeeeeeee
//031419736������������������������������PubDSK_1����������������135148280060348320ACEVEDO��������������������������������RUIZ��������������������������������������MAGALY��������������������������������������������������������������������������������0F19720614250010A+��2-��2Qÿt£O]WXFm°\-v´}»ÀkQt©r5b?k®¨^p£ LB O'¡_1%z«d¸tÇ{5v½w?XMjZo|dklJoOlqryv?!±nÿ%YjYõ������������������������������������������������������������������������������������������������7(��CSÿth~Vmwnq­l´}pV¨W dy>¶D{³ |ª%qysu¹¢b£q?¯¬w®b¹}¬Ìm9cNqYz}\`­¤ú´'Ùüw½5«OKë����������������������������������������������������������������������������������������������������������������������v 4çRÜKÑ·#&C§ëÂO3dÔ¥(Õ¤üCT\øÂd[

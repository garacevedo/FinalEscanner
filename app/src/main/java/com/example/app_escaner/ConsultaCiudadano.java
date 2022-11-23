package com.example.app_escaner;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_escaner.DB.ConexionPG;
import com.example.app_escaner.adaptadores.ListaCiudadanosAdapter;
import com.example.app_escaner.entidades.Ciudadanos;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.annotation.Documented;
import java.util.*;


public class ConsultaCiudadano extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static ConexionPG bd = new ConexionPG();
    public static ArrayList<Ciudadanos> resultadoConsultaCiudadanos;
    public ListaCiudadanosAdapter adapter;
    RecyclerView recyclerCiudadanosFilterSearch;
    SearchView txtBuscar;
    //CheckBox chkBxRequerido;
    Button btnDescargaReporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_ciudadano);

        txtBuscar = findViewById(R.id.txtBuscar);
//        chkBxRequerido = findViewById(R.id.chkBxNoRequerido);
        btnDescargaReporte = findViewById(R.id.btnDescargaReporte);


        if(checkPermission()){
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
            System.out.println("Permisos acpetadosssssssssss");
        }else{
            requestPermissions();
        }

        btnDescargaReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearPDF();

            }
        });

        init_reclicer();
        txtBuscar.setOnQueryTextListener(this);

        System.out.println("-----------------------------------------");

    }

    public void crearPDF(){
        try{

            bd.Database();
            resultadoConsultaCiudadanos = bd.consultarCiudadanos();
            //String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/EjemploPDF";

            String path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            File dir = new File(path);
            if (!dir.exists()){
                dir.mkdirs();
            }

            File file = new File (dir, "ciudadanos.pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            Document document= new Document();

            PdfWriter.getInstance(document, fileOutputStream);

            document.open();

            Paragraph titulo = new Paragraph(
                    "Lista de Ciudadanos \n\n\n",
                    FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLUE)
            );

            document.add(titulo);

            PdfPTable tabla = new PdfPTable(8);
            tabla.addCell("Número de Identificación");
            tabla.addCell("Nombre");
            tabla.addCell("Apellidos");
            tabla.addCell("Fecha de Nacimento");
            tabla.addCell("Rh");
            tabla.addCell("Grupo Sanguineo");
            tabla.addCell("Sexo");
            tabla.addCell("Requerido");

            for(int i = 0; i< resultadoConsultaCiudadanos.size(); i++){
                tabla.addCell(String.valueOf(resultadoConsultaCiudadanos.get(i).getNumero_identificacion()));
                tabla.addCell(resultadoConsultaCiudadanos.get(i).getNombre());
                tabla.addCell(resultadoConsultaCiudadanos.get(i).getApellidos());
                tabla.addCell(resultadoConsultaCiudadanos.get(i).getFecha_nacimento());
                tabla.addCell(String.valueOf(resultadoConsultaCiudadanos.get(i).getRh()));
                tabla.addCell(resultadoConsultaCiudadanos.get(i).getGrupo_sanguineo());
                tabla.addCell(resultadoConsultaCiudadanos.get(i).getSexo());
                tabla.addCell(resultadoConsultaCiudadanos.get(i).getRequerido());
            }

            document.add(tabla);

            document.close();
            System.out.println("Documentoo listooooooooooooooooo");
            Toast.makeText(this, "Archivo descargado correctamente. Revisar carpeta de Descargas del dispositivo.", Toast.LENGTH_LONG).show();
            System.out.println(path);

        }catch (FileNotFoundException  | DocumentException e){
            e.printStackTrace();
        }
    }



    private boolean checkPermission(){
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grandResults){
        if(requestCode == 200){
            if(grandResults.length > 0){
                boolean writeStorage = grandResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grandResults[1] == PackageManager.PERMISSION_GRANTED;

                if(writeStorage && readStorage){
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    public void init_reclicer() {
        resultadoConsultaCiudadanos = new ArrayList<>();

        recyclerCiudadanosFilterSearch = findViewById(R.id.recicler);
        recyclerCiudadanosFilterSearch.setLayoutManager((new LinearLayoutManager(this)));
        bd.Database();
        resultadoConsultaCiudadanos = bd.consultarCiudadanos();
        System.out.println("esoooooooooooooooooooooooooooooo");
        adapter = new ListaCiudadanosAdapter(resultadoConsultaCiudadanos);
        recyclerCiudadanosFilterSearch.setAdapter(adapter);

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrarCiudadanoBusqueda(s);
        return false;
    }
}
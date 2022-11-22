package com.example.app_escaner.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_escaner.R;
import com.example.app_escaner.entidades.Ciudadanos;
import com.example.app_escaner.MostrarCiudadanos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaCiudadanosAdapter extends RecyclerView.Adapter<ListaCiudadanosAdapter.CiudadanoViewHolder> {

    ArrayList<Ciudadanos> listaCiudadanos;
    ArrayList<Ciudadanos> listaOriginal;


    public ListaCiudadanosAdapter(ArrayList<Ciudadanos> listaCiudadanos){
        this.listaCiudadanos = listaCiudadanos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaCiudadanos);
    }

    @NonNull
    @Override
    public ListaCiudadanosAdapter.CiudadanoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_ciudadano, null, false);
        return new CiudadanoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaCiudadanosAdapter.CiudadanoViewHolder holder, int position) {
        System.out.println(listaCiudadanos.get(position).getNombre() +" aaaaaaaaa "+ listaCiudadanos.get(position).getNumero_identificacion() +" eeeeeeeeeeeeeee " + listaCiudadanos.get(position).getRequerido());
        holder.iconImage.setImageResource(listaCiudadanos.get(position).getIconImg());
        holder.viewNombre.setText(listaCiudadanos.get(position).getNombre());
        holder.viewIdentificacion.setText(String.valueOf(listaCiudadanos.get(position).getNumero_identificacion()));
        holder.viewRequerido.setText(listaCiudadanos.get(position).getRequerido());
    }

    @Override
    public int getItemCount() {
        return listaCiudadanos.size();
    }

    public void filtrarCiudadanoBusqueda(String txtBuscar){
        int longitud  = txtBuscar.length();
        if(longitud == 0){
            listaCiudadanos.clear();
            listaCiudadanos.addAll(listaOriginal);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Ciudadanos> collectionCiudadanos = listaCiudadanos.stream()
                        .filter(i ->i.getRequerido().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaCiudadanos.clear();
                listaCiudadanos.addAll(collectionCiudadanos);
            }else{
                for (Ciudadanos c:listaOriginal) {
                    if (c.getRequerido().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaCiudadanos.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class CiudadanoViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView viewNombre, viewIdentificacion, viewRequerido;
        public CiudadanoViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImg);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewIdentificacion = itemView.findViewById(R.id.viewIdentificacion);
            viewRequerido = itemView.findViewById(R.id.viewRequerido);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MostrarCiudadanos.class);
                    System.out.println("loooooooooooooooooo "+ listaCiudadanos.get(getAdapterPosition()).getId_ciudadano());
                    intent.putExtra("ID", listaCiudadanos.get(getAdapterPosition()).getId_ciudadano());
                    context.startActivity(intent);
                }
            });
        }
    }


}

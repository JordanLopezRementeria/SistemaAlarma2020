package es.jordan.sistemaalarma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorDatos extends RecyclerView.Adapter<AdaptadorDatos.ViewHolderDatos> implements View.OnClickListener {

    ArrayList<DatosColmena> miLista;
    private View.OnClickListener escuchador;

    public AdaptadorDatos(ArrayList<DatosColmena> miLista) {
        this.miLista = miLista;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View laVista = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist, null, false);
        laVista.setOnClickListener(this);
        return new ViewHolderDatos(laVista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(miLista.get(position));
    }

    @Override
    public int getItemCount() {
        if (miLista != null) return miLista.size();
        return 0;
    }

    @Override
    public void onClick(View v) {
        if (escuchador != null) escuchador.onClick(v);


    }

    //se genera manualmente
    public void setOnClickListener(View.OnClickListener escucha) {
        this.escuchador = escucha;
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView elNombre;
        ImageView laFoto;
        TextView laInformacion;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            elNombre = (TextView) itemView.findViewById(R.id.idNombre);
            laInformacion = (TextView) itemView.findViewById(R.id.idInformacion);
            laFoto = (ImageView) itemView.findViewById(R.id.idFoto);
        }

        public void asignarDatos(DatosColmena datosColmena) {
            //Se asigna los datos del objeto recibido a las variables
            elNombre.setText(datosColmena.getNombre());
            laInformacion.setText(datosColmena.getInformacion());
            laFoto.setImageResource(datosColmena.getFoto());
        }
    }
}


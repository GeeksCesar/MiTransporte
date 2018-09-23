package geeks.mitransporte.Menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import geeks.mitransporte.R;

public class AdapterRecyclerInfoCity extends RecyclerView.Adapter<AdapterRecyclerInfoCity.ViewHolder> {

    private Context context;
    private List<InfoCiudad> infoCiudadList;


    public AdapterRecyclerInfoCity(Context context, List<InfoCiudad> infoCiudadList) {
        this.context = context;
        this.infoCiudadList = infoCiudadList;
    }

    // Creamos la vista
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_infocity, parent, false);

        // Creamos la instancia de la clase estática del ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final InfoCiudad infoCiudad = infoCiudadList.get(position);

        holder.lugar.setText(infoCiudad.getLugar());
        holder.tipo.setText(infoCiudad.getTipo());
        holder.descripcion.setText(infoCiudad.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return infoCiudadList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView lugar, tipo,descripcion;
        CardView card_view;

        // Relacionamos los elementos de la vista
        public ViewHolder(View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.card_view);
            lugar = itemView.findViewById(R.id.lugar);
            tipo = itemView.findViewById(R.id.tipo);
            descripcion = itemView.findViewById(R.id.description);
        }

    }
}

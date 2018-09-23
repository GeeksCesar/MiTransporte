package geeks.mitransporte.Contoller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import geeks.mitransporte.Model.RouteAll;
import geeks.mitransporte.R;

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.viewHolder>{

    Context context ;
    List<RouteAll> routesList ;

    public RouteListAdapter(Context context, List<RouteAll> routesList) {
        this.context = context;
        this.routesList = routesList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rutas_lugar, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        RouteAll routeAll = routesList.get(position);

        holder.tvNumberRoute.setText(routeAll.getRouteNumber());
        holder.tvNameRoute.setText("Ruta # "+routeAll.getRouteNumber());
    }

    @Override
    public int getItemCount() {
        return routesList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView tvNumberRoute, tvNameRoute, tvPlacesRoute ;

        public viewHolder(View itemView) {
            super(itemView);

            tvNumberRoute = itemView.findViewById(R.id.tvNumberRuta);
            tvNameRoute = itemView.findViewById(R.id.tvNameRuta);
            tvPlacesRoute = itemView.findViewById(R.id.tvLugaresRoute);
        }
    }
}

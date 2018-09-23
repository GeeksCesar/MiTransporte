package geeks.mitransporte.Contoller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import geeks.mitransporte.Objeto.Places;
import geeks.mitransporte.R;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.viewHolder>{

    Context context;
    List<Places> placesList;

    public PlaceListAdapter(Context context, List<Places> placesList) {
        this.context = context;
        this.placesList = placesList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_place, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Places places = placesList.get(position);

        holder.tvPlaceName.setText(""+places.getName());
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder{

        TextView tvPlaceName;

        public viewHolder(View itemView) {
            super(itemView);
            tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
        }
    }
}

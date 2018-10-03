package geeks.mitransporte.Menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import geeks.mitransporte.Api.ApiService;
import geeks.mitransporte.Api.Service;
import geeks.mitransporte.Contoller.RouteListAdapter;
import geeks.mitransporte.Model.RouteAll;
import geeks.mitransporte.Objeto.LugarDetalle;
import geeks.mitransporte.R;
import geeks.mitransporte.Utils.RecyclerItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleLugar extends AppCompatActivity {

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String IMAGE = "IMAGE";
    public static final String CALIFICACIONES = "CALIFICACION";

    Bundle bundle;

    Toolbar toolbar;

    ApiService apiService;

    RecyclerView rvRutas;
    RecyclerView.LayoutManager layoutManager;

    ImageView imageLugar ;
    List<RouteAll> routeList;
    RouteListAdapter adapter ;

    int id_lugar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lugar);

        apiService = Service.getApiService();
        bundle = getIntent().getExtras();

        id_lugar = bundle.getInt(ID);
        String name = bundle.getString(NAME);

        Log.d(Service.TAG, "id_lugar: "+id_lugar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rvRutas = findViewById(R.id.rvRutasPlaces);
        imageLugar = findViewById(R.id.ivLugar);

        rvRutas.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvRutas.setLayoutManager(layoutManager);
        rvRutas.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvRutas.getContext(), LinearLayoutManager.VERTICAL);
        rvRutas.addItemDecoration(dividerItemDecoration);


        getInfoLugar(id_lugar);
        dataRoute(id_lugar);


        rvRutas.addOnItemTouchListener(new RecyclerItemClickListener(DetalleLugar.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

                RouteAll route = routeList.get(position);

                int id = route.getRouteId();
                Toast.makeText(DetalleLugar.this, "Id: "+id, Toast.LENGTH_SHORT).show();
            }
        }));


    }

    private void getInfoLugar(int id_lugar) {
        apiService.getDetalleLugar(id_lugar).enqueue(new Callback<LugarDetalle>() {
            @Override
            public void onResponse(Call<LugarDetalle> call, Response<LugarDetalle> response) {

                Log.d(Service.TAG, "response "+response);
                if (response.isSuccessful()) {
                    String name = response.body().getImagen();
                    Glide.with(DetalleLugar.this).load(name).into(imageLugar);
                }

            }

            @Override
            public void onFailure(Call<LugarDetalle> call, Throwable t) {
                Log.d(Service.TAG, "response "+t.getMessage());
            }
        });
    }

    private void dataRoute(int id){
        apiService.getRutasLugar(id).enqueue(new Callback<List<RouteAll>>() {
            @Override
            public void onResponse(Call<List<RouteAll>> call, Response<List<RouteAll>> response) {
                Log.d(Service.TAG, "response "+response);
                routeList = response.body();

                if (routeList.size() > 0){
                    adapter = new RouteListAdapter(DetalleLugar.this, routeList);
                    rvRutas.setAdapter(adapter);
                }else {

                }
            }

            @Override
            public void onFailure(Call<List<RouteAll>> call, Throwable t) {
                Log.d(Service.TAG, "response "+t.getMessage());
            }
        });

    }
}

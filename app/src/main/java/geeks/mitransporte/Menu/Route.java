package geeks.mitransporte.Menu;


import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import geeks.mitransporte.Api.ApiService;
import geeks.mitransporte.Api.Service;
import geeks.mitransporte.Contoller.RouteListAdapter;
import geeks.mitransporte.Model.RouteAll;
import geeks.mitransporte.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Route extends Fragment implements OnMapReadyCallback {

    View view ;
    Context context ;
    TextView tvCiudad;
    RecyclerView recicleRutas;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private BottomSheetBehavior bottomSheetBehavior;
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    boolean actualLocation = true;
    double latOrigin, longOrigin;
    boolean initial = true; //Defino si la actividad es inicial
    public static final int LOCATION_REQUEST_CODE = 1;

    ApiService apiService ;
    RouteListAdapter adapter ;

    RecyclerView.LayoutManager layoutManager;
    List<RouteAll> routeAlls ;

    public Route() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu_route, container, false);

        initView();

        return view ;
    }

    private void initView() {
        context = getActivity();
        apiService = Service.getApiService();

        tvCiudad = view.findViewById(R.id.tvCiudad);
        recicleRutas = view.findViewById(R.id.listRoute);
        final View peakView = view.findViewById(R.id.bottomSheetLayout);

        recicleRutas.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recicleRutas.setLayoutManager(layoutManager);
        recicleRutas.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recicleRutas.getContext(), LinearLayoutManager.VERTICAL);
        recicleRutas.addItemDecoration(dividerItemDecoration);

        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottomSheetLayout));

        getRoute();

        FragmentManager fragmentManager = getChildFragmentManager();
        if (mapFragment == null){
            mapFragment =  SupportMapFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.container_map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (initial) {
                    initial = false;
                    int height = (int) convertDpToPixel(42, context);
                    bottomSheetBehavior.setPeekHeight(height);
                    peakView.requestLayout();
                }
            }
        });


    }

    private void getRoute(){
        apiService.getRoute().enqueue(new Callback<List<RouteAll>>() {
            @Override
            public void onResponse(Call<List<RouteAll>> call, Response<List<RouteAll>> response) {

                Log.d(Service.TAG, "response: "+response);

                if (response.isSuccessful()){

                    routeAlls = response.body();
                    int count = routeAlls.size();

                    if (count > 0){
                        adapter = new RouteListAdapter(context, routeAlls);
                        recicleRutas.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<RouteAll>> call, Throwable t) {

            }
        });
    }

    private  float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        // Mostrar botón Mi Ubicación. Validar Permisos
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    if (actualLocation){
                        latOrigin = location.getLatitude();
                        longOrigin = location.getLongitude();

                        LatLng miPosicion = new LatLng(latOrigin, longOrigin);
                        actualLocation = false;

                        // Ubicar la Cámara en el mapa
                        mMap.addMarker( new MarkerOptions().position(miPosicion).title("Aquí"));

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(latOrigin, longOrigin))
                                .zoom(14)
                                .bearing(45)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                }
            });
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo para la ubicación
                showDialogLocation();
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }

    }

    private void showDialogLocation() {
        Dialog dialog = new Dialog(context, R.style.AppTheme);
        dialog.setTitle("Para ofrecerte una mejor experiencia, activa el permiso de ubicación");
        dialog.show();
    }
}

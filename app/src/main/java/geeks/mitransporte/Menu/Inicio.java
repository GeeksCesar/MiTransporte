package geeks.mitransporte.Menu;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import geeks.mitransporte.Api.ApiService;
import geeks.mitransporte.Api.Service;
import geeks.mitransporte.Contoller.PlaceListAdapter;
import geeks.mitransporte.Objeto.Places;
import geeks.mitransporte.R;
import geeks.mitransporte.Utils.RecyclerItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Inicio extends Fragment implements AppBarLayout.OnOffsetChangedListener{

    View view ;
    Context context ;
    RelativeLayout lnBuscarSitios, tvBuscarSitios ;
    RecyclerView reciclerInfoCiudad;
    RecyclerView.Adapter mAdapter;

    AppBarLayout barLayout;

    Dialog dialog ;
    TextView tvCerrarDIalog , tvNearMe ;
    EditText edLugar ;
    RecyclerView rvPlaces;
    RecyclerView.LayoutManager layoutManager;

    ApiService apiService ;
    List<Places> placesList ;
    PlaceListAdapter adapter;



    public Inicio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.menu_inicio, container, false);

        initWidget();

        tvBuscarSitios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              showDialogLugar();
            }
        });


        return  view;
    }

    private void initWidget() {

        apiService = Service.getApiService();
        context = getActivity();

        lnBuscarSitios = view.findViewById(R.id.lnBuscarSitiosToolbar);
        tvBuscarSitios = view.findViewById(R.id.tvBuscarSitios);
        barLayout = view.findViewById(R.id.appBar);

        /* Recicler view**/
        reciclerInfoCiudad = view.findViewById(R.id.reciclerInfoCiudad);
        reciclerInfoCiudad.setLayoutManager(new LinearLayoutManager(context));
        reciclerInfoCiudad.setNestedScrollingEnabled(false);  // Deshabilito el nestedScrollView para que los items se desplacen más rapido
        mAdapter = new AdapterRecyclerInfoCity(getActivity(), setDataDummyInfoCity());
        reciclerInfoCiudad.setAdapter(mAdapter);

        barLayout.addOnOffsetChangedListener(this);
    }

    private List<InfoCiudad> setDataDummyInfoCity() {
        List<InfoCiudad> listInfoCity = new ArrayList<>();
        listInfoCity.add(new InfoCiudad("Accidente", "Carrera 7", "Se ha presentado un accidente cerca del colegio la presentación, entre un carro y una motocicleta"));
        listInfoCity.add(new InfoCiudad("Obras", "Usco", "Se está realizando el intercambiador  frente a la Universidad Surcolombiana"));
        listInfoCity.add(new InfoCiudad("Manifestación", "Parque Santander", "Hay un grupo de personas manifestando frente a la gobernación del huila, tamponando la carrera 4"));

        return listInfoCity;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (Math.abs(verticalOffset) >= barLayout.getTotalScrollRange()){
            lnBuscarSitios.setVisibility(View.VISIBLE);
        }else {
            lnBuscarSitios.setVisibility(View.GONE);
        }
    }

    private void showDialogLugar(){
        dialog = new Dialog(context, R.style.FullScreenDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_lugar_seleccion);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        tvCerrarDIalog = dialog.findViewById(R.id.tvCerrarDialoag);
        edLugar = dialog.findViewById(R.id.edLugar);
        tvNearMe = dialog.findViewById(R.id.tvNearMe);
        rvPlaces = dialog.findViewById(R.id.rvRutas);

        rvPlaces.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        rvPlaces.setLayoutManager(layoutManager);
        rvPlaces.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvPlaces.getContext(), LinearLayoutManager.VERTICAL);
        rvPlaces.addItemDecoration(dividerItemDecoration);


        edLugar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edLugar.getText().toString().trim() == ""){

                }else {
                    //Toast.makeText(context, "text: "+editable, Toast.LENGTH_SHORT).show();
                    getPlaces(editable.toString());
                }
            }
        });



        tvCerrarDIalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });


        rvPlaces.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Places places = placesList.get(position);

                int id = places.getId() ;
                String name = places.getName();

                Intent intent = new Intent(context, DetalleLugar.class);
                intent.putExtra(DetalleLugar.ID, id);
                intent.putExtra(DetalleLugar.NAME, name);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        }));


        dialog.show();
    }

    private void getPlaces(final String place){

        apiService.getPlaces(place).enqueue(new Callback<List<Places>>() {
            @Override
            public void onResponse(Call<List<Places>> call, Response<List<Places>> response) {
                Log.d(Service.TAG, "response: "+response);
                if (response.isSuccessful()){
                    placesList = response.body();
                    int count = placesList.size();

                    if (count > 0){
                        adapter = new PlaceListAdapter(context, placesList);
                        rvPlaces.setAdapter(adapter);
                    }else  {

                    }

                }else {

                }

            }

            @Override
            public void onFailure(Call<List<Places>> call, Throwable t) {

            }
        });

    }
}

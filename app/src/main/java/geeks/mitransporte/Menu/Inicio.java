package geeks.mitransporte.Menu;


import android.app.Dialog;
import android.content.Context;
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

import java.util.List;

import geeks.mitransporte.Api.ApiService;
import geeks.mitransporte.Api.Service;
import geeks.mitransporte.Contoller.PlaceListAdapter;
import geeks.mitransporte.Objeto.Places;
import geeks.mitransporte.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Inicio extends Fragment implements AppBarLayout.OnOffsetChangedListener{

    View view ;
    Context context ;
    RelativeLayout lnBuscarSitios, tvBuscarSitios ;
    RecyclerView reciclerInfoCiudad;

    AppBarLayout barLayout;

    Dialog dialog ;
    TextView tvCerrarDIalog , tvNearMe ;
    EditText edLugar ;
    RecyclerView rvRutas ;
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
        reciclerInfoCiudad = view.findViewById(R.id.reciclerInfoCiudad);
        barLayout = view.findViewById(R.id.appBar);

        barLayout.addOnOffsetChangedListener(this);
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
        rvRutas = dialog.findViewById(R.id.rvRutas);

        rvRutas.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        rvRutas.setLayoutManager(layoutManager);
        rvRutas.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvRutas.getContext(), LinearLayoutManager.VERTICAL);
        rvRutas.addItemDecoration(dividerItemDecoration);


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
                        rvRutas.setAdapter(adapter);
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

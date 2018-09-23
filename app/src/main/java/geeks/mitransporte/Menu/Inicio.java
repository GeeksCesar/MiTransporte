package geeks.mitransporte.Menu;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import geeks.mitransporte.R;


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

        tvCerrarDIalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

        dialog.show();
    }
}

package geeks.mitransporte.Menu;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import geeks.mitransporte.R;


public class Inicio extends Fragment implements AppBarLayout.OnOffsetChangedListener{

    View view ;
    Context context ;
    RelativeLayout lnBuscarSitios, tvBuscarSitios ;

    AppBarLayout barLayout;

    public Inicio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.menu_inicio, container, false);

        initWidget();

        lnBuscarSitios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "LinearLayout", Toast.LENGTH_SHORT).show();
            }
        });

        tvBuscarSitios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Textview", Toast.LENGTH_SHORT).show();
            }
        });


        return  view;
    }

    private void initWidget() {

        context = getActivity();

        lnBuscarSitios = view.findViewById(R.id.lnBuscarSitiosToolbar);
        tvBuscarSitios = view.findViewById(R.id.tvBuscarSitios);
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
}

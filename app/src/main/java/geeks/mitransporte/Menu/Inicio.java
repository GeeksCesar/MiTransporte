package geeks.mitransporte.Menu;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import geeks.mitransporte.R;


public class Inicio extends Fragment implements AppBarLayout.OnOffsetChangedListener{

    View view ;
    Context context ;
    RelativeLayout lnBuscarSitios, tvBuscarSitios ;

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

        tvCerrarDIalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

        dialog.show();
    }
}

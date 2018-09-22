package geeks.mitransporte.Menu;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import geeks.mitransporte.R;


public class Perfil extends Fragment {

    View view ;
    Context context ;

    public Perfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu_perfil, container, false);


        return view ;
    }

}

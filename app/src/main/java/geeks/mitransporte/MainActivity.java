package geeks.mitransporte;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import geeks.mitransporte.Menu.Inicio;
import geeks.mitransporte.Menu.Perfil;
import geeks.mitransporte.Menu.Route;
import geeks.mitransporte.Utils.BottomNavigationViewHelper;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity {

    Context context;
    BottomNavigationView navigation;
    int PERMISSION_ALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        navigation  = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        setFragment(0);
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_inicio:
                    setFragment(0);
                    return true;

                case R.id.nav_rutas:
                    setFragment(1);
                    return true;

                case R.id.nav_pefil:
                    setFragment(2);
                    return true;

            }
            return false;
        }
    };

    public void setFragment(int pos){
        Fragment fragment = null;

        switch (pos) {

            case 0:
                fragment = new Inicio();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, "INICIO").commit();
                break;

            case 1:
                fragment = new Route();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, "ROUTE").commit();
                break;

            case 2:
                fragment = new Perfil();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, "PERFIL  ").commit();
                break;


        }
    }

}

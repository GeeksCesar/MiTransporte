package geeks.mitransporte;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import geeks.mitransporte.Utils.SliderAdapter;
import geeks.mitransporte.Utils.UsuarioPreferences;

public class ScreenIntro extends AppCompatActivity {

    Context context ;
    ViewPager viewPager ;
    LinearLayout layoutContenedor ;

    private TextView[] mDots ;
    private SliderAdapter sliderAdapter;

    private Button btnNext;
    private Button btnBack ;
    private int mCurrentPage ;
    private String txtButton ;

    //Prefrences
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String session;
    int number ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_screen_intro);

        context = ScreenIntro.this ;
        viewPager = findViewById(R.id.viewPager);
        layoutContenedor = findViewById(R.id.lnContenedor);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        viewPager.addOnPageChangeListener(viewListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtButton = btnNext.getText().toString();

                preferences = getSharedPreferences(UsuarioPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                editor = preferences.edit();

                if (txtButton.equals("Siguiente")){
                    viewPager.setCurrentItem(mCurrentPage + 1);
                }else if (txtButton.equals("Finalizar")){
                    startActivity(new Intent(ScreenIntro.this, MainActivity.class));
                    finish();
                    editor.putString(UsuarioPreferences.KEY_SESSION, "SessionSuccess") ;
                    editor.putInt(UsuarioPreferences.KEY_NUMBER, 1);
                    editor.commit();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        getSessionUser();
    }

    private void getSessionUser() {
        session = UsuarioPreferences.getInstance(context).getSessionUser();
        number = UsuarioPreferences.getInstance(context).getNumberSession();

        if (session.equals("SessionSuccess") && number == 1){
            startActivity(new Intent(context, SplashScreen.class));
            finish();
        }else {

        }

    }

    public void addDotsIndicator(int position){
        mDots = new TextView[4];
        layoutContenedor.removeAllViews();

        for (int i=0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.whiteTransparent));

            layoutContenedor.addView(mDots[i]);
        }

        if (mDots.length > 0 ){
            mDots[position].setTextColor(getResources().getColor(R.color.md_text_white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);

            mCurrentPage = position ;

            if (position == 0){
                btnNext.setEnabled(true);
                btnBack.setEnabled(false);
                btnBack.setVisibility(View.INVISIBLE);

                btnNext.setText("Siguiente");
                btnBack.setText("");
            }else if (position == mDots.length -1){
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);

                btnNext.setText("Finalizar");
                btnBack.setText("Anterior");
            }else {
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);

                btnNext.setText("Siguiente");
                btnBack.setText("Anterior");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}

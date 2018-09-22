package geeks.mitransporte.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UsuarioPreferences {

    public static final String SHARED_PREF_NAME = "Usuario";
    public static final String KEY_SESSION = "session";
    public static final String KEY_NUMBER = "number";

    private static Context mContext ;
    private static UsuarioPreferences mInstance;

    private UsuarioPreferences(Context context){
        mContext = context;
    }

    public static synchronized UsuarioPreferences getInstance(Context context){
        if (mInstance == null)
            mInstance = new UsuarioPreferences(context);
        return mInstance;
    }

    public String getSessionUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SESSION, "SessionFailed");
    }

    public Integer getNumberSession(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_NUMBER, 0);
    }
}

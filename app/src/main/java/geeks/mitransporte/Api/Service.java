package geeks.mitransporte.Api;

public class Service {

    public static final String TAG = "Test" ;

    public static final String URL = "http://home.matrisk.co/hack/public/" ;

    public static ApiService getApiService(){
        return RetrofitClient.getClient(URL).create(ApiService.class);
    }

}

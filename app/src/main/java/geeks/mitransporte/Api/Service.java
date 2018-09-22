package geeks.mitransporte.Api;

public class Service {

    public static final String TAG = "Test" ;

    public static final String URL = "http://develop.segurosayg.com/hackathon/public/" ;

    public static ApiService getApiService(){
        return RetrofitClient.getClient(URL).create(ApiService.class);
    }

}

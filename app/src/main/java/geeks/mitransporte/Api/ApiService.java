package geeks.mitransporte.Api;

import java.util.List;

import geeks.mitransporte.Model.RouteAll;
import geeks.mitransporte.Objeto.Places;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api/get_routes")
    Call<List<RouteAll>> getRoute() ;

    @GET("api/get_places")
    Call<List<Places>> getPlaces(@Query("lugar") String Place) ;

}

package geeks.mitransporte.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RouteAll {

    @SerializedName("route_id")
    @Expose
    private String routeId;
    @SerializedName("route_number")
    @Expose
    private String routeNumber;

    /*@SerializedName("location_name")
    @Expose
    private List<PlacesRoute> placesRouteList = null; */


    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    /*
    public List<PlacesRoute> getPlacesRouteList() {
        return placesRouteList;
    }

    public void setPlacesRouteList(List<PlacesRoute> placesRouteList) {
        this.placesRouteList = placesRouteList;
    } */
}

package io.github.marceltanuri.frameworks.restam3.demo.model;

/**
 * Represents a geolocation with latitude and longitude.
 *
 * @author Marcel Tanuri
 */
public class Geolocalizacao{
    private String lat;
    private String lng;

    /**
     * Creates a new geolocation.
     *
     * @param lat the latitude
     * @param lng the longitude
     */
    public Geolocalizacao(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * Gets the latitude.
     *
     * @return the latitude
     */
    public String getLat() {
        return lat;
    }

    /**
     * Sets the latitude.
     *
     * @param lat the latitude
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * Gets the longitude.
     *
     * @return the longitude
     */
    public String getLng() {
        return lng;
    }

    /**
     * Sets the longitude.
     *
     * @param lng the longitude
     */
    public void setLng(String lng) {
        this.lng = lng;
    }

}

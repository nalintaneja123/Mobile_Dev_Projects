package com.example.saipavanraju.inclass09;

import java.util.ArrayList;

/**
 * Created by saipavanraju on 3/26/18.
 */

public class Trips {

    ArrayList<latlong> points;

    @Override
    public String toString() {
        return "Trips{" +
                "tripsArray=" + points +
                '}';
    }

    public ArrayList<latlong> getData(){
        return this.points;
    }

    public static class latlong{
        String latitude,longitude;

        @Override
        public String toString() {
            return "latlong{" +
                    "latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    '}';
        }
    }

}

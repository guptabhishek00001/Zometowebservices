package com.example.abhishek.zomatowebservice;

/**
 * Created by Abhishek on 9/18/2017.
 */

  class Restaurant {

   private String restaurant_name,
            restaurant_cuisines,
            restaurant_address,
            restaurant_image,
            restaurant_lat,
            restaurant_long,
            restaurant_rating;

    public Restaurant(String restaurant_name, String restaurant_cuisines, String restaurant_address,
                      String restaurant_image, String restaurant_lat,
                       String restaurant_long, String restaurant_rating) {
        this.restaurant_name = restaurant_name;
        this.restaurant_cuisines = restaurant_cuisines;
        this.restaurant_address = restaurant_address;
        this.restaurant_image = restaurant_image;
        this.restaurant_lat = restaurant_lat;
        this.restaurant_long = restaurant_long;
        this.restaurant_rating = restaurant_rating;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_cuisines() {
        return restaurant_cuisines;
    }

    public void setRestaurant_cuisines(String restaurant_cuisines) {
        this.restaurant_cuisines = restaurant_cuisines;
    }

    public String getRestaurant_address() {
        return restaurant_address;
    }

    public void setRestaurant_address(String restaurant_address) {
        this.restaurant_address = restaurant_address;
    }

    public String getRestaurant_image() {
        return restaurant_image;
    }

    public void setRestaurant_image(String restaurant_image) {
        this.restaurant_image = restaurant_image;
    }

    public String getRestaurant_lat() {
        return restaurant_lat;
    }

    public void setRestaurant_lat(String restaurant_lat) {
        this.restaurant_lat = restaurant_lat;
    }

    public String getRestaurant_long() {
        return restaurant_long;
    }

    public void setRestaurant_long(String restaurant_long) {
        this.restaurant_long = restaurant_long;
    }

    public String getRestaurant_rating() {
        return restaurant_rating;
    }

    public void setRestaurant_rating(String restaurant_rating) {
        this.restaurant_rating = restaurant_rating;
    }
}

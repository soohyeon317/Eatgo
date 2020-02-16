package kr.co.fastcampus.eatgo.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepositoryImpl() {
        this.restaurants.add(new Restaurant(1004L, "Bob zip", "Seoul"));
        this.restaurants.add(new Restaurant(2020L, "Cyber Food", "Seoul"));
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurants;
    }

    @Override
    public Restaurant findById(Long id) {
        return this.restaurants.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        restaurant.setId(1234L);
        this.restaurants.add(restaurant);
        return restaurant;
    }


}

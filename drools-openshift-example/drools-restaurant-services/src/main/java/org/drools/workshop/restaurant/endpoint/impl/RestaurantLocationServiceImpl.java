package org.drools.workshop.restaurant.endpoint.impl;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.drools.workshop.restaurant.endpoint.exception.BusinessException;
import org.drools.workshop.restaurant.endpoint.api.RestaurantLocationService;
import org.drools.workshop.restaurant.model.Restaurant;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class RestaurantLocationServiceImpl implements RestaurantLocationService {

   

    public RestaurantLocationServiceImpl() {
        
    }

    @Override
    public List<Restaurant> findRestaurantsNearMe(double lon, double lat) throws BusinessException {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(new Restaurant("Pizza Hut", 15.0, 30.0));
        restaurants.add(new Restaurant("Papa John's", 30.0, 45.0));
        restaurants.add(new Restaurant("Burger King", 0.0, 15.0));
        return restaurants;
    }

    
    
    

}

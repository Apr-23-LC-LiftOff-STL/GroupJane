package org.rally.backend.restaurantarm.data;
import org.rally.backend.restaurantarm.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository <Restaurant,Integer> {

}

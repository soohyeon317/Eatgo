package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockRestaurantRepository();
        mockRestaurantMenuItemRepository();
        mockReviewRepository();

        restaurantService = new RestaurantService(
                restaurantRepository, menuItemRepository, reviewRepository
        );
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        restaurants.add(restaurant);

        given(restaurantRepository.findAllByAddressStartsWithAndCategoryId("Seoul", 1L))
                .willReturn(restaurants);

        given(restaurantRepository.findById(1004L))
                .willReturn(Optional.of(restaurant));
    }

    private void mockRestaurantMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItem.builder()
                .name("Kimchi")
                .build());

        given(menuItemRepository.findAllByRestaurantId(1004L))
                .willReturn(menuItems);
    }

    private void mockReviewRepository() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder()
                .name("BeRyong")
                .score(1)
                .description("Bad")
                .build());

        given(reviewRepository.findAllByRestaurantId(1004L))
                .willReturn(reviews);
    }

    @Test
    public void getRestaurantWithExisted() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        verify(menuItemRepository).findAllByRestaurantId(eq(1004L));
        verify(reviewRepository).findAllByRestaurantId(eq(1004L));

        assertThat(restaurant.getId()).isEqualTo(1004L);

        MenuItem menuItem = restaurant.getMenuItems().get(0);

        assertThat(menuItem.getName()).isEqualTo("Kimchi");

        Review review = restaurant.getReviews().get(0);

        assertThat(review.getDescription()).isEqualTo("Bad");
    }

    @Test
    public void getRestaurantWithNotExisted() {
        assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantService.getRestaurant(404L);
        });
    }

    @Test
    public void getRestaurants() {
        String region = "Seoul";
        Long categoryId = 1L;

        List<Restaurant> restaurants = restaurantService.getRestaurants(region, categoryId);
        Restaurant restaurant = restaurants.get(0);

        assertThat(restaurant.getId()).isEqualTo(1004L);
    }

}
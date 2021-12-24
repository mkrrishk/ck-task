package ee.cleankitchen.deliveryservice;

import ee.cleankitchen.orderservice.OrderServiceClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryServiceTest {

    DeliveryServiceClient deliveryServiceClient;
    OrderServiceClient orderServiceClient;

    @BeforeAll
    void beforeAll() {
        deliveryServiceClient = new DeliveryService(new OrderServiceMock());
    }

    @Test
    @DisplayName("Given Monday with 3 Orders at 12:30 and 4 Orders at 18:30, Should return 10:30 and 12:30 " +
            "as available Delivery Times")
    void givenMondayThenReturnTwoDeliveryTimes() {
        List<LocalTime> availableDeliveryTimes = deliveryServiceClient.
                getAvailableDeliveryTimes(LocalDate.of(2021, 12, 20));

        assertAll("Delivery Times",
                () -> assertNotNull(availableDeliveryTimes),
                () -> assertEquals(2, availableDeliveryTimes.size()),
                () -> assertIterableEquals(availableDeliveryTimes,
                        List.of(LocalTime.of(10, 30),
                                LocalTime.of(12, 30)
                        )
                )
        );
    }

    @Test
    @DisplayName("Given Tuesday with 2 Orders at 10:30, Should return 12:30 and 18:30 as available Delivery Times")
    void givenTuesdayThenReturnTwoDeliveryTimes() {
        List<LocalTime> availableDeliveryTimes = deliveryServiceClient.
                getAvailableDeliveryTimes(LocalDate.of(2021, 12, 21));

        assertAll("Delivery Times",
                () -> assertNotNull(availableDeliveryTimes),
                () -> assertEquals(2, availableDeliveryTimes.size()),
                () -> assertIterableEquals(availableDeliveryTimes,
                        List.of(LocalTime.of(12, 30),
                                LocalTime.of(18, 30)
                        )
                )
        );
    }

    @Test
    @DisplayName("Given Friday with 2 Orders each at 10:30, 12:30 and 18:30, Should not return any available " +
            "Delivery Times")
    void givenFridayThenReturnNoDeliveryTimes() {
        List<LocalTime> availableDeliveryTimes = deliveryServiceClient.
                getAvailableDeliveryTimes(LocalDate.of(2021, 12, 24));

        assertAll("Delivery Times",
                () -> assertNotNull(availableDeliveryTimes),
                () -> assertEquals(0, availableDeliveryTimes.size()),
                () -> assertIterableEquals(availableDeliveryTimes, List.of())
        );
    }

    @Test
    @DisplayName("Given Sunday with no Orders, Should return 10:30, 12:30 and 18:30 as available Delivery Times")
    void givenSundayThenReturnAllDeliveryTimes() {
        List<LocalTime> availableDeliveryTimes = deliveryServiceClient.
                getAvailableDeliveryTimes(LocalDate.of(2021, 12, 26));

        assertAll("Delivery Times",
                () -> assertNotNull(availableDeliveryTimes),
                () -> assertEquals(3, availableDeliveryTimes.size()),
                () -> assertIterableEquals(availableDeliveryTimes,
                        List.of(LocalTime.of(10, 30),
                                LocalTime.of(12, 30),
                                LocalTime.of(18, 30)
                        )
                )
        );
    }

}
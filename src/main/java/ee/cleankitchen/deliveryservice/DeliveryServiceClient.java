package ee.cleankitchen.deliveryservice;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DeliveryServiceClient {

    /**
     * Get list of Available Delivery Times for the given {@code date}.
     */
    List<LocalTime> getAvailableDeliveryTimes(LocalDate date);

}

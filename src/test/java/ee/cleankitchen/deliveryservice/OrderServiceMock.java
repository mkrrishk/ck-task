package ee.cleankitchen.deliveryservice;

import ee.cleankitchen.orderservice.OrderServiceClient;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class OrderServiceMock implements OrderServiceClient {

    public List<Map<String, Object>> getBy(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<Map<String, Object>> result;

        switch (dayOfWeek.getValue()) {
            case 1: // Monday
                result = List.of(
                        Map.of("date", date, "time", LocalTime.of(12, 30), "orderId", "123", "customerId", "a-111"),
                        Map.of("date", date, "time", LocalTime.of(12, 30), "orderId", "223", "customerId", "a-211"),
                        Map.of("date", date, "time", LocalTime.of(12, 30), "orderId", "323", "customerId", "a-311"),
                        Map.of("date", date, "time", LocalTime.of(18, 30), "orderId", "423", "customerId", "a-411"),
                        Map.of("date", date, "time", LocalTime.of(18, 30), "orderId", "523", "customerId", "a-511"),
                        Map.of("date", date, "time", LocalTime.of(18, 30), "orderId", "623", "customerId", "a-611"),
                        Map.of("date", date, "time", LocalTime.of(18, 30), "orderId", "723", "customerId", "a-711")
                );
                break;
            case 2: // Tuesday
                result = List.of(
                        Map.of("date", date, "time", LocalTime.of(10, 30), "orderId", "123", "customerId", "a-111"),
                        Map.of("date", date, "time", LocalTime.of(10, 30), "orderId", "223", "customerId", "a-211")
                );
                break;
            case 5: // Friday
                result = List.of(
                        Map.of("date", date, "time", LocalTime.of(10, 30), "orderId", "123", "customerId", "a-111"),
                        Map.of("date", date, "time", LocalTime.of(10, 30), "orderId", "223", "customerId", "a-211"),
                        Map.of("date", date, "time", LocalTime.of(12, 30), "orderId", "323", "customerId", "a-311"),
                        Map.of("date", date, "time", LocalTime.of(12, 30), "orderId", "423", "customerId", "a-411"),
                        Map.of("date", date, "time", LocalTime.of(18, 30), "orderId", "523", "customerId", "a-511"),
                        Map.of("date", date, "time", LocalTime.of(18, 30), "orderId", "623", "customerId", "a-611")
                );
                break;
            case 7: // Sunday
                result = List.of();
                break;
            default:
                result =  List.of(
                        Map.of("date", date, "time", LocalTime.of(10, 30), "orderId", "123", "customerId", "a-111"),
                        Map.of("date", date, "time", LocalTime.of(12, 30), "orderId", "223", "customerId", "a-211"),
                        Map.of("date", date, "time", LocalTime.of(18, 30), "orderId", "323", "customerId", "a-311")
                );
                break;
        }
        return result;
    }

}

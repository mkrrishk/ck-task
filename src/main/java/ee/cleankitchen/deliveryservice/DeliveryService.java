package ee.cleankitchen.deliveryservice;

import ee.cleankitchen.orderservice.OrderServiceClient;
import ee.cleankitchen.util.CollectionUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryService implements DeliveryServiceClient {

    private final int limitForMonday = 4;
    private final int limitForOtherDays = 2;
    private final List<LocalTime> deliveryTimes =
            List.of(LocalTime.of(10, 30),
                    LocalTime.of(12, 30),
                    LocalTime.of(18, 30));

    private final OrderServiceClient orderServiceClient;

    public DeliveryService(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }


    @Override
    public List<LocalTime> getAvailableDeliveryTimes(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<Map<String, Object>> existingOrders = orderServiceClient.getBy(date);

        if (CollectionUtils.isNullOrEmpty(existingOrders)) {
            return deliveryTimes;
        }

        // Get Delivery Times along with order count as Map for Existing Orders
        Map<Object, Long> existingOrdersDeliveryTimesMap = getDeliveryTimesWithOrderCount(existingOrders);

        // Get Default Delivery Times List as Map with Order Count Set to Zero
        Map<Object, Long> defaultDeliveryTimesMap = deliveryTimes.stream()
                .collect(Collectors.toMap(deliveryTime -> deliveryTime, deliveryTime -> 0L));

        // Combine Map of Default & Existing Delivery Times to account for Missing Delivery Times
        Map<Object, Long> deliveryTimesOrderCountMap = Stream
                .concat(defaultDeliveryTimesMap.entrySet().stream(), existingOrdersDeliveryTimesMap.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingLong(Map.Entry::getValue)));

        return deliveryTimesOrderCountMap
                .entrySet().stream()
                .filter(entry -> entry.getValue() < getDeliveryLimit(dayOfWeek))
                .map(entry -> (LocalTime) entry.getKey())
                .sorted().collect(Collectors.toList());
    }

    private Map<Object, Long> getDeliveryTimesWithOrderCount(List<Map<String, Object>> existingOrders) {
        return existingOrders.stream()
                .collect(Collectors.groupingBy(stringObjectMap -> stringObjectMap.get("time"), Collectors.counting()));
    }

    private int getDeliveryLimit(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.MONDAY ? limitForMonday : limitForOtherDays;
    }

}

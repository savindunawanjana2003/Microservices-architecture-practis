package lk.ijse.parkingspacesevice.feign;

import lk.ijse.parkingspacesevice.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(name = "parking-spot-service") // Eureka Service Name එක නිවැරදියි
public interface BookingServiceClient {

    // Python Flask endpoint එකට ගැළපෙන පරිදි URL එක /api/v1/parking/bookings/spot/{spotId} විය යුතුය
    @GetMapping("/api/v1/parking/bookings/spot/{spotId}")
    List<BookingDTO> getBookingsBySpotId(@PathVariable("spotId") String spotId);
}
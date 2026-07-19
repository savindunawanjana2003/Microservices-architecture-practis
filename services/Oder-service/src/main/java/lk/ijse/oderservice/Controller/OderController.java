package lk.ijse.oderservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/order")
public class OderController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/orders/test")
    public ResponseEntity<?> testMicroserviceCommunication() {
        System.out.println("--- Order Service එකේ Function එක Trigger වුණා! 🚀 ---");

        try {
            // 💡 වෙනත් සර්විස් එකක නම (e.g., ITEM-SERVICE) සහ නිවැරදි controller path එක විතරක් දෙන්න
            String allItemUrl = "http://ITEM-SERVICE/api/v1/item/all";

            String res = restTemplate.getForObject(allItemUrl, String.class);
            return new ResponseEntity<>("Order received, response from Item Service: " + res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // මොකක්ද වෙච්ච error එක කියලා console එකේ බලාගන්න
            return new ResponseEntity<>("Order Service එකට Request එක ආවා, හැබැයි අනිත් Service එකට කතා කරන්න බැරි වුණා! ❌ Error: " + e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }





    @PostMapping("/orders/save")
    public ResponseEntity<?> saveOder() {
        System.out.println("--- Order Service එකේ Function එක Trigger වුණා! 🚀 ---");

        try {
            String allItemUrl = "http://ITEM-SERVICE/api/v1/item/all";

            String res = restTemplate.getForObject(allItemUrl, String.class);
            return new ResponseEntity<>("Order received, response from Item Service: " + res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // මොකක්ද වෙච්ච error එක කියලා console එකේ බලාගන්න
            return new ResponseEntity<>("Order Service එකට Request එක ආවා, හැබැයි අනිත් Service එකට කතා කරන්න බැරි වුණා! ❌ Error: " + e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


}
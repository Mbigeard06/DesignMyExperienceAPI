package com.utopia.designmyexperience_api.controller;

import com.utopia.designmyexperience_api.model.Activity;
import com.utopia.designmyexperience_api.model.Offering;
import com.utopia.designmyexperience_api.model.Service;
import com.utopia.designmyexperience_api.service.OfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offerings")
public class OfferingController {

    private final OfferingService offeringService;

    @Autowired
    public OfferingController(OfferingService offeringService) {
        this.offeringService = offeringService;
    }

    /**
     * Get all offerings for a business owner.
     * @param businessOwnerId the business owner ID
     * @return List of offerings
     */
    @GetMapping("/business-owner/{businessOwnerId}")
    public ResponseEntity<?> getOfferingsByBusinessOwner(@PathVariable int businessOwnerId) {
        try {
            List<Offering> offerings = offeringService.getOfferingsByBusinessOwner(businessOwnerId);
            return ResponseEntity.ok(offerings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while fetching offerings", "details", e.getMessage()));
        }
    }

    /**
     * Get a single offering by ID.
     * @param id offering ID
     * @return Offering object if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOfferingById(@PathVariable int id) {
        try {
            Offering offering = offeringService.getOffering(id);
            if (offering != null) {
                return ResponseEntity.ok(offering);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Offering not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while fetching offering", "details", e.getMessage()));
        }
    }

    /**
     * Get an activity by ID.
     * @param id activity ID
     * @return Activity object if found
     */
    @GetMapping("/activities/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable int id) {
        try {
            Activity activity = offeringService.getActivity(id);
            if (activity != null) {
                return ResponseEntity.ok(activity);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Activity not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while fetching activity", "details", e.getMessage()));
        }
    }

    /**
     * Get a service by ID.
     * @param id service ID
     * @return Service object if found
     */
    @GetMapping("/services/{id}")
    public ResponseEntity<?> getServiceById(@PathVariable int id) {
        try {
            Service service = offeringService.getService(id);
            if (service != null) {
                return ResponseEntity.ok(service);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Service not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while fetching service", "details", e.getMessage()));
        }
    }
}

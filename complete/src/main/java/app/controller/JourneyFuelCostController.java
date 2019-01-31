package app.controller;

import app.model.JourneyFuelCost;
import app.service.JourneyFuelCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class JourneyFuelCostController {

    @Autowired
    private JourneyFuelCostService journeyFuelCostService;

    @GetMapping("/journey/cost")
    @ResponseBody
    public ResponseEntity<JourneyFuelCost> getJourneyFuelCost(@RequestParam("date") String date,
                                                              @RequestParam("fuelType") String fuelType,
                                                              @RequestParam("mpg") Integer mpg,
                                                              @RequestParam("mileage") Integer mileage) {

        JourneyFuelCost journeyFuelCost = journeyFuelCostService.getJourneyFuelCost(date, fuelType, mpg, mileage);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(journeyFuelCost);
    }

}

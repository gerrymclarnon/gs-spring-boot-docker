package app.service;

import app.model.JourneyFuelCost;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JourneyFuelCostService {

    public static final double LITRES_PER_GALLON = 4.54609;
    public static final String FUEL_TYPE_PETROL = "petrol";
    public static final String FUEL_TYPE_DIESEL = "diesel";

    public JourneyFuelCost getJourneyFuelCost(final String date,
                                              final String fuelType,
                                              final Integer mpg,
                                              final Integer mileage) {
        JsonParser jsonParser = new BasicJsonParser();
        JourneyFuelCost journeyFuelCost = new JourneyFuelCost();

        try (
                InputStream allRoadFuelPricesStream = new ClassPathResource("WeeklyRoadFuelPrices.json").getInputStream();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(allRoadFuelPricesStream))) {

            String allRoadFuelPricesJsonString = reader.lines()
                    .collect(Collectors.joining("\n"));

            Map<String, Object> allRoadFuelPrices = jsonParser.parseMap(allRoadFuelPricesJsonString);
            Optional<Map.Entry<String, Object>> latestRoadFuelCosts = allRoadFuelPrices.entrySet().stream().reduce((first, second) -> second);
            Map<String, Object> roadFuelPricesForLatestWeek = (Map<String, Object>) latestRoadFuelCosts.get().getValue();

            Map<String, Object> filteredRoadFuelCosts = allRoadFuelPrices.entrySet().stream()
                    .filter(roadFuelCost -> roadFuelCost.getKey().equalsIgnoreCase(date))
                    .collect(Collectors.toMap(filteredRoadFuelCost -> filteredRoadFuelCost.getKey(), filteredRoadFuelCost -> filteredRoadFuelCost.getValue()));

            Optional<Map.Entry<String, Object>> firstRoadFuelCost = filteredRoadFuelCosts.entrySet().stream().findFirst();
            Map<String, Object> roadFuelPricesForWeek = (Map<String, Object>) firstRoadFuelCost.get().getValue();

            Double latestPumpPrice = null;
            Double pumpPrice = null;
            Double dutyRate = null;

            if (fuelType.equalsIgnoreCase(FUEL_TYPE_PETROL)) {
                latestPumpPrice = (Double) roadFuelPricesForLatestWeek.get("pumpPriceULSP");
                pumpPrice = (Double) roadFuelPricesForWeek.get("pumpPriceULSP");
                dutyRate = (Double) roadFuelPricesForWeek.get("dutyRateULSP");
            } else if (fuelType.equalsIgnoreCase(FUEL_TYPE_DIESEL)) {
                latestPumpPrice = (Double) roadFuelPricesForLatestWeek.get("pumpPriceULSD");
                pumpPrice = (Double) roadFuelPricesForWeek.get("pumpPriceULSD");
                dutyRate = (Double) roadFuelPricesForWeek.get("dutyRateULSD");
            }

            Double litresOfFuelUsed = (mileage / mpg) * LITRES_PER_GALLON;
            Double costInPence = litresOfFuelUsed * pumpPrice;
            Double dutyInPence = litresOfFuelUsed * dutyRate;
            Double todayDifferenceInPence = (litresOfFuelUsed * latestPumpPrice) - costInPence;

            journeyFuelCost.setCostInPence(costInPence);
            journeyFuelCost.setDutyInPence(dutyInPence);
            journeyFuelCost.setTodayDifferenceInPence(todayDifferenceInPence);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the WeeklyRoadFuelPrices.json file", e);
        }

        return journeyFuelCost;
    }
}

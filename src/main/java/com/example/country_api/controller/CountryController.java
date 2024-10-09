package com.example.country_api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    // Mock method to generate fresh country data
    private Map<Integer, String> generateCountries() {
        Map<Integer, String> countries = new HashMap<>();
        countries.put(1, "India");
        countries.put(2, "USA");
        countries.put(3, "Japan");
        return countries;
    }

    // POST method to add a new country (Point 2)
    @PostMapping("/add")
    public ResponseEntity<String> addCountry(@RequestBody Map<String, String> requestBody) {
        Map<Integer, String> countries = generateCountries(); 
        String countryName = requestBody.get("name");
        if (countryName == null || countryName.isEmpty()) {
            return new ResponseEntity<>("Country name is required", HttpStatus.BAD_REQUEST);
        }
        int newId = countries.size() + 1;
        countries.put(newId, countryName);
        return new ResponseEntity<>("Country added with ID " + newId, HttpStatus.CREATED);
    }

    // PUT method to update an existing country (Point 3)
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCountry(@PathVariable int id, @RequestBody Map<String, String> requestBody) {
        Map<Integer, String> countries = generateCountries();
        String countryName = requestBody.get("name");
        if (!countries.containsKey(id)) {
            return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND);
        }
        if (countryName == null || countryName.isEmpty()) {
            return new ResponseEntity<>("Country name is required", HttpStatus.BAD_REQUEST);
        }
        countries.put(id, countryName);
        return new ResponseEntity<>("Country updated", HttpStatus.OK);
    }

    // DELETE method to remove a country (Point 4)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable int id) {
        Map<Integer, String> countries = generateCountries();
        if (!countries.containsKey(id)) {
            return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND);
        }
        countries.remove(id);
        return new ResponseEntity<>("Country deleted", HttpStatus.NO_CONTENT);
    }

    // PATCH method to partially update a country's name (Point 5)
    @PatchMapping("/patch/{id}")
    public ResponseEntity<String> patchCountryName(@PathVariable int id, @RequestBody Map<String, String> requestBody) {
        Map<Integer, String> countries = generateCountries();
        String countryName = requestBody.get("name");
        if (!countries.containsKey(id)) {
            return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND);
        }
        if (countryName == null || countryName.isEmpty()) {
            return new ResponseEntity<>("Country name is required", HttpStatus.BAD_REQUEST);
        }
        countries.put(id, countryName);
        return new ResponseEntity<>("Country name updated", HttpStatus.OK);
    }

    // GET method to retrieve a country by ID with status code validation (Point 1)
    @GetMapping(value = "/get/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> getCountryById(@PathVariable int id, @RequestHeader("Accept") String acceptHeader) {
        Map<Integer, String> countries = generateCountries();
        String country = countries.get(id);
        if (country != null) {
            if (acceptHeader.contains("xml")) {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(new Country(id, country)); 
            }
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(new Country(id, country));  
        } else {
            return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND); 
        }
    }

    // GET method to search country by name using query parameters (Point 2)
    @GetMapping("/search")
    public ResponseEntity<?> searchCountryByName(@RequestParam(value = "name", required = false) String name) {
        Map<Integer, String> countries = generateCountries();
        if (name != null && !name.isEmpty()) {
            for (Map.Entry<Integer, String> entry : countries.entrySet()) {
                if (entry.getValue().equalsIgnoreCase(name)) {
                    return new ResponseEntity<>(new Country(entry.getKey(), entry.getValue()), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND); 
        } else {
            return new ResponseEntity<>("Query parameter 'name' is missing or empty", HttpStatus.BAD_REQUEST); 
        }
    }

    // GET method to retrieve a country by its code (Point 3)
    @GetMapping("/code/{code}")
    public ResponseEntity<String> getCountryByCode(@PathVariable String code) {
        Map<String, String> countryCodes = new HashMap<>();
        countryCodes.put("IN", "India");
        countryCodes.put("US", "USA");
        countryCodes.put("JP", "Japan");

        String country = countryCodes.get(code.toUpperCase());
        if (country != null) {
            return new ResponseEntity<>(country, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Country code not found", HttpStatus.NOT_FOUND); 
        }
    }

    // POST method to add multiple countries (bulkAdd) (Point 13)
    @PostMapping("/bulkAdd")
    public ResponseEntity<String> addMultipleCountries(@RequestBody Map<Integer, String> countries) {
        if (countries == null || countries.isEmpty()) {
            return new ResponseEntity<>("No data provided", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Countries added", HttpStatus.CREATED);
    }

    // GET method to test different types of request/response body (Point 14)
    @GetMapping(value = "/responseType/{id}", produces = { "application/json", "application/xml", "text/plain" })
    public ResponseEntity<?> getCountryByIdWithResponseType(@PathVariable int id, @RequestHeader("Accept") String acceptHeader) {
        Map<Integer, String> countries = generateCountries();
        String country = countries.get(id);

        if (country != null) {
            if (acceptHeader.contains("xml")) {
                return ResponseEntity.ok().body(new Country(id, country));  
            } else if (acceptHeader.contains("text")) {
                return ResponseEntity.ok().body("Country: " + country); 
            } else {
                return ResponseEntity.ok().body(new Country(id, country)); 
            }
        } else {
            return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND); 
        }
    }

    // Country class for JSON and XML responses
    static class Country {
        private int id;
        private String name;

        public Country(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}

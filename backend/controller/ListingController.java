package com.skillswap.backend.controller;

import com.skillswap.backend.model.Listing;
import com.skillswap.backend.service.ListingService;
import com.skillswap.backend.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/listing")
@CrossOrigin("http://localhost:5173")
public class ListingController {
    @Autowired
    private ListingService listingService;

    @PostMapping("/add")
    public Listing addListing(@RequestParam Long userId,
                              @RequestParam String title,
                              @RequestParam String description,
                              @RequestParam double price,
                              @RequestParam String category,
                              @RequestParam(required = false) String imageUrl ) {
        return listingService.addListing(userId, title, description, price, category, imageUrl);
    }


    @GetMapping("/all")
        public List<Listing> getListings() {
        return listingService.getAllListings();
    }

    @GetMapping("{userId}")
    public List<Listing> getListingUser(@PathVariable Long userId) {
        return listingService.getListingsForUser(userId);
    }

    @GetMapping("/search")
    public List<Listing> search(@RequestParam String category){
        return listingService.searchByCategory(category);
    }

    @PostMapping("/buy/{id}")
    public Listing buyListing(@PathVariable Long id) {
        return listingService.buyListing(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteListing(@PathVariable long id){
        listingService.deleteListing(id);
    }
}
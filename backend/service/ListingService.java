package com.skillswap.backend.service;

import com.skillswap.backend.model.Listing;
import com.skillswap.backend.model.User;
import com.skillswap.backend.repository.ListingRepository;
import com.skillswap.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListingService {
    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    public Listing addListing(Long userId, String title, String description, double price, String category, String imageUrl){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found. Double check please."));

        Listing listing = new Listing();
        listing.setTitle(title);
        listing.setDescription(description);
        listing.setPrice(price);
        listing.setCategory(category);
        listing.setUser(user);
        listing.setImageUrl(imageUrl);

        return listingRepository.save(listing);

    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public List<Listing> getListingsForUser(Long userId) {
        return listingRepository.findByUserId(userId);
    }

    public List<Listing> searchByCategory(String category) {
        return listingRepository.findByCategory(category);
    }

    public Listing buyListing(long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        listing.setIsSold(true);
        return listingRepository.save(listing);

    }

    public void deleteListing(long id) {
        listingRepository.deleteById(id);
    }


}

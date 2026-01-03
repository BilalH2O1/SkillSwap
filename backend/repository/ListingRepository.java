package com.skillswap.backend.repository;

import com.skillswap.backend.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByUserId(Long userId);

    List<Listing> findByCategory(String category);
}
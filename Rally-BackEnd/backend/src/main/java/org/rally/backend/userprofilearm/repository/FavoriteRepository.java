package org.rally.backend.userprofilearm.repository;

import org.rally.backend.userprofilearm.model.FavoritePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<FavoritePost, Integer> {
}

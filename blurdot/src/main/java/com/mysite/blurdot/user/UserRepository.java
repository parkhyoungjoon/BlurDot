package com.mysite.blurdot.user;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<SiteUser, Integer> {
    boolean existsByUserEmail(String userEmail);
    boolean existsByUserName(String userName);
    Optional<SiteUser> findByUserEmail(String userEmail);
}
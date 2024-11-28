package com.mysite.blurdot.email;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.blurdot.user.SiteUser;

@Repository
public interface EmailRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByUserAndVerificationCode(SiteUser user, String code);
}
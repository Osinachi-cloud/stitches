package com.stitches.repository;

import com.stitches.enums.Role;
import com.stitches.model.AppUser;
import com.stitches.model.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByEmail(String email);
    Page<AppUser> findByEmailContaining(String name, Pageable pageable);

//    @Query(value = "SELECT * FROM AppUser", nativeQuery = true)
    Page<AppUser> findAllByRole(
            Role role,
            Pageable pageable
    );
}

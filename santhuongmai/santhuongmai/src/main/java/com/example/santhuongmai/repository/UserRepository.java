package com.example.santhuongmai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.santhuongmai.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query(value = "select * from user where email = :value or phone = :value limit 1", nativeQuery = true)
    Optional<User> findUserByEmailOrPhone(String value);
}

package com.example.santhuongmai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.santhuongmai.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact,Long> {
    
}
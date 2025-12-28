package com.messenger.repository;

import com.messenger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // findByPhoneNumber etc. dacă e nevoie
}

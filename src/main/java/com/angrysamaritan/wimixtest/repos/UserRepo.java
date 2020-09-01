package com.angrysamaritan.wimixtest.repos;

import com.angrysamaritan.wimixtest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("select u from User u where u.firstName = :firstName")
    Page<User> getUsersByFirstName(String firstName, Pageable pageable);
}

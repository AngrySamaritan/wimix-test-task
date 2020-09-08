package com.angrysamaritan.wimixtest.repos;
import com.angrysamaritan.wimixtest.model.Message;
import com.angrysamaritan.wimixtest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("select u from User u join u.profile p where p.firstName = :firstName")
    Page<User> getUsersByFirstName(String firstName, Pageable pageable);


    @Query("SELECT u FROM User u WHERE u.username = :username")
    User getUserByUsername(String username);
}

package com.angrysamaritan.wimixtest.repositories;

import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {

    @Query("select p from Profile p  where p.firstName = :firstName")
    Page<Profile> getUsersByFirstName(String firstName, Pageable pageable);

    Profile getProfileByUserId(long id);

}

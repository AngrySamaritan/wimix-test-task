package com.angrysamaritan.wimixtest.repositories;

import com.angrysamaritan.wimixtest.model.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
}

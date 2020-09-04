package com.angrysamaritan.wimixtest.repos;

import com.angrysamaritan.wimixtest.model.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends CrudRepository<Profile, Long> {
}

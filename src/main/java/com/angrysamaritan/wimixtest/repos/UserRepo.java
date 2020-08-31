package com.angrysamaritan.wimixtest.repos;

import com.angrysamaritan.wimixtest.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
}

package com.angrysamaritan.wimixtest.repositories;

import com.angrysamaritan.wimixtest.model.MailLetter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends CrudRepository<MailLetter, Long> {
}

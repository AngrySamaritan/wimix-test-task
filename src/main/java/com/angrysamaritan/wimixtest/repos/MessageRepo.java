package com.angrysamaritan.wimixtest.repos;

import com.angrysamaritan.wimixtest.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Long> {
}

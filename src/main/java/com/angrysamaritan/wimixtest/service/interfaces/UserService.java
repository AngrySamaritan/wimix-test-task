package com.angrysamaritan.wimixtest.service.interfaces;

import com.angrysamaritan.wimixtest.dto.ProfileDto;
import com.angrysamaritan.wimixtest.model.User;

public interface UserService {

    long getIdByUsername(String username);
}

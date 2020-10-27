package com.angrysamaritan.wimixtest.service;

import java.util.Map;

public interface MailService {

    void addToQueue(String to, Map<String, Object> templateModel, String templateName, String subject);

}

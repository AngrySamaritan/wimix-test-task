package com.angrysamaritan.wimixtest.DTO;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
public class ErrorsDto {

    private Map<String, String> fieldErrors = new HashMap<>();

    private String globalError;
}

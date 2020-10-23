package com.angrysamaritan.wimixtest.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorsDto {

    @Builder.Default
    private Map<String, String> fieldErrors = new HashMap<>();

    private String globalError;

    private String message;
}

package com.angrysamaritan.wimixtest.DTO;

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
}
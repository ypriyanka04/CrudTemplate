package com.myapp.crudtemplate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO{
    private Long id;
    private String username;
    private String email;
    private String mobile;
    private String address;

}

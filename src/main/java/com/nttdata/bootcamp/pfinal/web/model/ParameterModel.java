package com.nttdata.bootcamp.pfinal.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParameterModel {

    @JsonIgnore
    private String id;
    
    @NotBlank(message="Parameter cannot be null or empty")
	private String parameter;
    
    @NotBlank(message="Name cannot be null or empty")
    private String value;
   
     
}

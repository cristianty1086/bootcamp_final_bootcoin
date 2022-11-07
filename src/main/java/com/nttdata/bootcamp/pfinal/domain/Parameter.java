package com.nttdata.bootcamp.pfinal.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Parameter {
	
	@Id
    private String id;
	
	@NotNull
	private String parameter;
	
	@NotNull
	private String value;
	
}

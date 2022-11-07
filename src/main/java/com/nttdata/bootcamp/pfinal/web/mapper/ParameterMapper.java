package com.nttdata.bootcamp.pfinal.web.mapper;

import com.nttdata.bootcamp.pfinal.domain.Parameter;
import com.nttdata.bootcamp.pfinal.web.model.ParameterModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ParameterMapper {

	 Parameter modelToEntity (ParameterModel model);
	 
	 ParameterModel entityToModel (Parameter event);
	 
	 @Mapping(target = "id", ignore = true)
	 void update(@MappingTarget Parameter entity, Parameter updateEntity);
}

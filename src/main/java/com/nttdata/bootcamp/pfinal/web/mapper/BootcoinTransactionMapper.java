package com.nttdata.bootcamp.pfinal.web.mapper;

import com.nttdata.bootcamp.pfinal.domain.BootcoinTransaction;
import com.nttdata.bootcamp.pfinal.web.model.BootcoinTransactionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BootcoinTransactionMapper {

    BootcoinTransaction modelToEntity (BootcoinTransactionModel model);

    BootcoinTransactionModel entityToModel (BootcoinTransaction event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget BootcoinTransaction entity, BootcoinTransaction updateEntity);
}

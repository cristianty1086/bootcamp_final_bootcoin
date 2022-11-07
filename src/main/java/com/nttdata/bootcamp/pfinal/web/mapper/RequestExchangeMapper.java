package com.nttdata.bootcamp.pfinal.web.mapper;

import com.nttdata.bootcamp.pfinal.domain.BootcoinTransaction;
import com.nttdata.bootcamp.pfinal.domain.RequestExchange;
import com.nttdata.bootcamp.pfinal.web.model.BootcoinTransactionModel;
import com.nttdata.bootcamp.pfinal.web.model.RequestExchangeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RequestExchangeMapper {

    RequestExchange modelToEntity (RequestExchangeModel model);

    RequestExchangeModel entityToModel (RequestExchange event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget RequestExchange entity, RequestExchange updateEntity);
}

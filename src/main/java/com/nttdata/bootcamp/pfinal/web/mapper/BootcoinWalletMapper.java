package com.nttdata.bootcamp.pfinal.web.mapper;

import com.nttdata.bootcamp.pfinal.domain.BootcoinWallet;
import com.nttdata.bootcamp.pfinal.web.model.BootcoinWalletModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BootcoinWalletMapper {

    BootcoinWallet modelToEntity (BootcoinWalletModel model);

    BootcoinWalletModel entityToModel (BootcoinWallet event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget BootcoinWallet entity, BootcoinWallet updateEntity);
}

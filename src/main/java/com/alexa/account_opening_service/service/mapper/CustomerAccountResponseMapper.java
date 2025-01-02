package com.alexa.account_opening_service.service.mapper;

import com.alexa.account_opening_service.dto.AccountResponseDTO;
import com.alexa.account_opening_service.entity.AccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link AccountRequest} and its DTO {@link AccountResponseDTO}.
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CustomerAccountResponseMapper extends EntityMapper<AccountResponseDTO, AccountRequest> {
    default AccountRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        // TODO: fix setter with lombok
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setId(id);
        return accountRequest;
    }
}

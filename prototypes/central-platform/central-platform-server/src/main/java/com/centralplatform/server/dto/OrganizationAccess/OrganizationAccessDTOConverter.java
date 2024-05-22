package com.centralplatform.server.dto.OrganizationAccess;

import com.centralplatform.server.dto.Organization.OrganizationDTOConverter;
import com.centralplatform.server.model.Organization.Organization;
import com.centralplatform.server.model.OrganizationAccess.OrganizationAccess;
import com.centralplatform.server.util.Converters.GenericConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class OrganizationAccessDTOConverter implements GenericConverter<OrganizationAccess, OrganizationAccessDTO> {
    @Override
    public OrganizationAccessDTO apply(OrganizationAccess organizationAccess) {
        OrganizationDTOConverter organizationDTOConverter = new OrganizationDTOConverter();
        OrganizationAccessDTO dto = new OrganizationAccessDTO();
        dto.setId(organizationAccess.getId());
        dto.setOrganization(organizationDTOConverter.convert(organizationAccess.getOrganization()));
        dto.setAllowedOrganizations(organizationDTOConverter.convert(organizationAccess.getAllowedOrganizations()));
        return dto;
    }

    @Override
    public <V> Function<V, OrganizationAccessDTO> compose(Function<? super V, ? extends OrganizationAccess> before) {
        return GenericConverter.super.compose(before);
    }

    @Override
    public <V> Function<OrganizationAccess, V> andThen(Function<? super OrganizationAccessDTO, ? extends V> after) {
        return GenericConverter.super.andThen(after);
    }

    @Override
    public OrganizationAccessDTO convert(OrganizationAccess input) {

        return GenericConverter.super.convert(input);
    }

    @Override
    public List<OrganizationAccessDTO> convert(List<OrganizationAccess> input) {
        return GenericConverter.super.convert(input);
    }
}

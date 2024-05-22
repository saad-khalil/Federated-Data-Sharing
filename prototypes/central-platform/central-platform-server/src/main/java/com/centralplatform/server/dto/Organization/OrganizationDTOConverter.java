package com.centralplatform.server.dto.Organization;

import com.centralplatform.server.model.Organization.Organization;
import com.centralplatform.server.util.Converters.GenericConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class OrganizationDTOConverter implements GenericConverter<Organization, OrganizationDTO> {
    @Override
    public OrganizationDTO apply(Organization organization) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setOrganizationType(organization.getOrganizationType());
        return dto;
    }

    @Override
    public <V> Function<V, OrganizationDTO> compose(Function<? super V, ? extends Organization> before) {
        return GenericConverter.super.compose(before);
    }

    @Override
    public <V> Function<Organization, V> andThen(Function<? super OrganizationDTO, ? extends V> after) {
        return GenericConverter.super.andThen(after);
    }

    @Override
    public OrganizationDTO convert(Organization input) {

        return GenericConverter.super.convert(input);
    }

    @Override
    public List<OrganizationDTO> convert(List<Organization> input) {
        return GenericConverter.super.convert(input);
    }
}

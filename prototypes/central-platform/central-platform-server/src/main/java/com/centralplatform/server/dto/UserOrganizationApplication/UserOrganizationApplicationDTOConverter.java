package com.centralplatform.server.dto.UserOrganizationApplication;

import com.centralplatform.server.model.Organization.Organization;
import com.centralplatform.server.model.UserOrganizationApplication.UserOrganizationApplication;
import com.centralplatform.server.util.Converters.GenericConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class UserOrganizationApplicationDTOConverter implements GenericConverter<UserOrganizationApplication, UserOrganizationApplicationDTO> {
    @Override
    public UserOrganizationApplicationDTO apply(UserOrganizationApplication organizationApplication) {
        UserOrganizationApplicationDTO dto = new UserOrganizationApplicationDTO();
        dto.setId(organizationApplication.getId());
        dto.setStatus(organizationApplication.getStatus());
        dto.setEmail(organizationApplication.getUser().getEmail());
        dto.setOrganizationId(organizationApplication.getOrganizationId());
        dto.setCreatedAt(organizationApplication.getCreatedAt());
        dto.setUpdatedAt(organizationApplication.getUpdatedAt());
        return dto;
    }

    @Override
    public <V> Function<V, UserOrganizationApplicationDTO> compose(Function<? super V, ? extends UserOrganizationApplication> before) {
        return GenericConverter.super.compose(before);
    }

    @Override
    public <V> Function<UserOrganizationApplication, V> andThen(Function<? super UserOrganizationApplicationDTO, ? extends V> after) {
        return GenericConverter.super.andThen(after);
    }

    @Override
    public UserOrganizationApplicationDTO convert(UserOrganizationApplication input) {

        return GenericConverter.super.convert(input);
    }

    @Override
    public List<UserOrganizationApplicationDTO> convert(List<UserOrganizationApplication> input) {
        return GenericConverter.super.convert(input);
    }
}

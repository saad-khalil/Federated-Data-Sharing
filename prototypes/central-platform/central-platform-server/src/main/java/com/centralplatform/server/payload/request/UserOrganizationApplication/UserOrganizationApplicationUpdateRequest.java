package com.centralplatform.server.payload.request.UserOrganizationApplication;

import com.centralplatform.server.model.UserOrganizationApplication.UserOrganizationApplication;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserOrganizationApplicationUpdateRequest extends UserOrganizationApplicationRequest {

    UserOrganizationApplication userOrganizationApplication;
}

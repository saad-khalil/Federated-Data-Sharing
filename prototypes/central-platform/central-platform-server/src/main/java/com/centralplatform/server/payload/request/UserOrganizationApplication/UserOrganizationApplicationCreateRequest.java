package com.centralplatform.server.payload.request.UserOrganizationApplication;

import com.centralplatform.server.model.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserOrganizationApplicationCreateRequest extends UserOrganizationApplicationRequest {
      private String organizationId;
}

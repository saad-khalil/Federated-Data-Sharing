package com.centralplatform.server.payload.request.OrganizationAccess;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrganizationAccessCreateRequest extends OrganizationAccessRequest {
      private String organizationId;
}

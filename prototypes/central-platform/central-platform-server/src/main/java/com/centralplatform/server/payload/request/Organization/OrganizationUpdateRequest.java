package com.centralplatform.server.payload.request.Organization;

import com.centralplatform.server.model.Organization.Organization;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
public class OrganizationUpdateRequest extends OrganizationRequest {

    Organization organization;
}

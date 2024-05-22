package com.centralplatform.server.dto.Organization;

import com.centralplatform.server.model.Organization.OrganizationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OrganizationDTO {

     UUID id;
     String name;

     OrganizationType organizationType;

}

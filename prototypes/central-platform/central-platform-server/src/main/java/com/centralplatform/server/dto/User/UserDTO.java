package com.centralplatform.server.dto.User;

import com.centralplatform.server.model.Role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email;
    private Role role;
    private String organizationId;
}

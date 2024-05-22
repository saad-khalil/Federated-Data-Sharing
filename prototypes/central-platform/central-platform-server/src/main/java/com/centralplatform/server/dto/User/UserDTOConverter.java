package com.centralplatform.server.dto.User;

import com.centralplatform.server.model.User.User;
import com.centralplatform.server.util.Converters.GenericConverter;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter implements GenericConverter<User, UserDTO> {
    /**
     * Applies this function to the given argument.
     *
     * @param user the function argument
     * @return the function result
     */
    @Override
    public UserDTO apply(User user) {
        UserDTO output = new UserDTO();

        output.setEmail(user.getEmail());
        output.setRole(user.getRole());
        output.setOrganizationId(user.getOrganizationId());
        return output;
    }
}

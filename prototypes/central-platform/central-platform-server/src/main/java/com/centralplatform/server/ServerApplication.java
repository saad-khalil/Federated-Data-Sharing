package com.centralplatform.server;

import com.centralplatform.server.model.Organization.Organization;
import com.centralplatform.server.model.Role.Role;
import com.centralplatform.server.model.User.User;
import com.centralplatform.server.repository.User.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.centralplatform.server.repository.Organization.OrganizationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ServerApplication implements ApplicationRunner {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final OrganizationRepository organizationRepository;

	public ServerApplication(UserRepository userRepository, PasswordEncoder passwordEncoder, OrganizationRepository organizationRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.organizationRepository = organizationRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	@Override
	public void run(ApplicationArguments args) throws Exception {
		createAdminUsersForOrganizations();
	}

	private void createAdminUsersForOrganizations() {
		List<Organization> organizations = organizationRepository.findAll();

		for (Organization organization : organizations) {
			User adminUser = User.builder()
					.firstname("Admin")
					.lastname(organization.getName())
					.email("admin@" + organization.getName().toLowerCase().replace(" ", "") + ".com")
					.registeredAt(new Date())
					.password(passwordEncoder.encode("adminPassword"))
					.organizationId(organization.getId().toString())
					.userDescription("Admin user for " + organization.getName())
					.role(Role.ADMIN)
					.build();

			userRepository.save(adminUser);
		}
	}
}

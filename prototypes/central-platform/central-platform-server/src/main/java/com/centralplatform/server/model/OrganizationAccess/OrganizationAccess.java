package com.centralplatform.server.model.OrganizationAccess;

import com.centralplatform.server.model.Location.Location;
import com.centralplatform.server.model.Organization.Organization;
import com.centralplatform.server.model.User.User;
import com.centralplatform.server.model.UserOrganizationApplication.StatusType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization_access")
public class OrganizationAccess {
    @Id
    @UuidGenerator
    private UUID id;

    @OneToOne
    @JoinColumn(name = "organization_id")
    @JsonBackReference
    private Organization organization;

    @ManyToMany
    @JoinTable(
            name = "organization_access_allowed_organizations",
            joinColumns = @JoinColumn(name = "organization_access_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    @JsonIgnoreProperties("organizationAccess") // Prevents infinite recursion
    private List<Organization> allowedOrganizations;

}






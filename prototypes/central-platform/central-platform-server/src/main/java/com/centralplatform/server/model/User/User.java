package com.centralplatform.server.model.User;

import com.centralplatform.server.model.Role.Role;
import com.centralplatform.server.model.Token.Token;
import com.centralplatform.server.model.UserOrganizationApplication.UserOrganizationApplication;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @UuidGenerator
    private UUID id;
    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email;
    private String password;
    private String organizationId;

    @Column(columnDefinition = "TEXT")
    private String userDescription;
    private Date registeredAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
    @Column(name = "user_organization_applications")
    @JsonManagedReference(value = "user-organization-applications")
    @OneToMany(mappedBy = "user")
    private List<UserOrganizationApplication> userOrganizationApplications;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

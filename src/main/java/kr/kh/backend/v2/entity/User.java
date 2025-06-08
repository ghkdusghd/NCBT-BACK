package kr.kh.backend.v2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails, OAuth2User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private OauthType oauth;

    private String roles;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Record> scores = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Complaint> complaints = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Bookmark> bookmarks = new ArrayList<>();

    @Transient
    private Map<String, Object> attributes;

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles != null && !roles.isEmpty() ?
                List.of(roles.split(",")).stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()) :
                new ArrayList<>();
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

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return nickname;
    }
}
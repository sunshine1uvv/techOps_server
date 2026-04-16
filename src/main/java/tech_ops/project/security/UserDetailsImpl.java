package tech_ops.project.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech_ops.project.entity.User;
import tech_ops.project.entity.UserStatus;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String name;
    private String surname;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private final boolean isAccountNonLocked;

    public UserDetailsImpl(Long id, String username, String name, String surname, String password, Collection<? extends GrantedAuthority> authorities, boolean isAccountNonLocked) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.authorities = authorities;
        this.isAccountNonLocked = isAccountNonLocked;
    }

    public static UserDetailsImpl build(User user) {
        boolean isAccountNonLocked = user.getStatus() != UserStatus.BLOCKED;
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getPasswordHash(),
                Collections.singletonList(authority),
                isAccountNonLocked);
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }


    @Override
    public boolean isAccountNonExpired() {
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

    public Long getId() {
        return id;
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }


    public String getUsername() {
        return username;
    }
}

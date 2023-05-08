package com.challenge_java.challenge_java;

import com.challenge_java.challenge_java.model.entity.ERole;
import com.challenge_java.challenge_java.model.entity.Phone;
import com.challenge_java.challenge_java.model.entity.Role;
import com.challenge_java.challenge_java.model.entity.User;
import com.challenge_java.challenge_java.model.services.RoleServiceImpl;
import com.challenge_java.challenge_java.model.services.UserDetailsImpl;
import com.challenge_java.challenge_java.model.services.UserDetailsServiceImpl;
import com.challenge_java.challenge_java.model.services.UserServicesImpl;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserServicesImpl userServices;

    @MockBean
    private RoleServiceImpl roleService;

    @MockBean
    private PasswordEncoder encoder;

    @Before
    public void setUp() throws Exception {
        this.userDetailsService = Mockito.mock(UserDetailsServiceImpl.class);
        this.roleService = Mockito.mock(RoleServiceImpl.class);
        this.encoder = Mockito.mock(PasswordEncoder.class);
        this.userServices = Mockito.mock(UserServicesImpl.class);
    }

    @Test
    public void loadUserByUsernameFound() {

        User user = create();

        when(userDetailsService.loadUserByUsername("prueba")).thenReturn(UserDetailsImpl.build(user));

        UserDetails found = userDetailsService.loadUserByUsername("prueba");


        AssertionsForInterfaceTypes.assertThat(found.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void loadUserByEmailFound() {

        User user = create();

        when(userDetailsService.loadUserByEmail("user@gmail.com")).thenReturn(UserDetailsImpl.build(user));

        UserDetails found = userDetailsService.loadUserByEmail("user@gmail.com");

        AssertionsForInterfaceTypes.assertThat(found.getUsername()).isEqualTo(user.getUsername());
    }

    private User create(){
        /* Create new user's account */
        when(userServices.save(any(User.class))).then(new Answer<User>() {
            Long secuencia = 8L;

            @Override
            public User answer(InvocationOnMock invocation) throws Throwable {
                User user = invocation.getArgument(0);
                user.setId(secuencia++);
                return user;
            }
        });

        User user = new User(
                "prueba",
                "prueba",
                "user@gmail.com",
                encoder.encode("P1234567")
        );

        Role role = new Role(ERole.ROLE_USER);
        role.setId(1);

        when(roleService.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));

        Role userRole = roleService.findByName(ERole.ROLE_USER).get();

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        Phone phoneList = new Phone(
                1L,
                10,
                "13213131321312"
        );

        Set<Phone> setPhone = new HashSet<>();
        setPhone.add(phoneList);
        user.setPhone(setPhone);

        user = userServices.save(user);

        return user;
    }
}

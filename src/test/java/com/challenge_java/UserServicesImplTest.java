package com.challenge_java;

import com.challenge_java.model.entity.ERole;
import com.challenge_java.model.entity.Phone;
import com.challenge_java.model.entity.Role;
import com.challenge_java.model.entity.User;
import com.challenge_java.model.services.RoleServiceImpl;
import com.challenge_java.model.services.UserDetailsServiceImpl;
import com.challenge_java.model.services.UserServicesImpl;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServicesImplTest {

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
    public void findByUsernameTest(){

        User user = createTest();

        when(userServices.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userServices.findByUsername(user.getUsername());

        AssertionsForInterfaceTypes.assertThat(foundUser.get().getUsername()).isEqualTo(foundUser.get().getUsername());
    }

    @Test
    public void existsByUsernameTest(){

        User user = createTest();
        Boolean ressult = true;
        when(userServices.existsByUsername(user.getUsername())).thenReturn(ressult);

        Boolean foundUser = userServices.existsByUsername(user.getUsername());

        AssertionsForInterfaceTypes.assertThat(foundUser).isEqualTo(ressult);
    }

    @Test
    public void existsByEmailTest(){

        User user = createTest();
        Boolean ressult = true;
        when(userServices.existsByEmail(user.getUsername())).thenReturn(ressult);

        Boolean foundUser = userServices.existsByEmail(user.getUsername());

        AssertionsForInterfaceTypes.assertThat(foundUser).isEqualTo(ressult);
    }

    @Test
    public void update(){

        User user = createTest();

        when(userServices.update(user)).thenReturn(user);

        User foundUser = userServices.update(user);

        AssertionsForInterfaceTypes.assertThat(foundUser).isEqualTo(user);
    }

    @Test
    public void save(){

        User user = createTest();

        when(userServices.save(user)).thenReturn(user);

        User foundUser = userServices.save(user);

        AssertionsForInterfaceTypes.assertThat(foundUser).isEqualTo(user);
    }

    private User createTest(){
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

        return user;
    }


}

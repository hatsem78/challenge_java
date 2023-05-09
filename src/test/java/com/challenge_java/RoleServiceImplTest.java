package com.challenge_java;

import com.challenge_java.model.entity.ERole;
import com.challenge_java.model.entity.Role;
import com.challenge_java.model.services.RoleServiceImpl;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RoleServiceImplTest {

    @MockBean
    private RoleServiceImpl roleService;
    @Before
    public void setUp() throws Exception {
        this.roleService = Mockito.mock(RoleServiceImpl.class);
    }


    @Test
    public void whenValidName_thenAccountSave() {

        when(roleService.save(any(Role.class))).then(new Answer<Role>() {
            Integer secuencia = 8;

            @Override
            public Role answer(InvocationOnMock invocation) throws Throwable {
                Role role = invocation.getArgument(0);
                role.setId(secuencia++);
                return role;
            }
        });

        Role role = new Role(ERole.ROLE_USER);
        role.setId(1);

        assertThat(role.getId() == 1);
    }

    @Test
    public void whenValidName_thenAccountShouldBeFound() {

        Role account = new Role(ERole.ROLE_MODERATOR);
        account.setId(1);

        when(roleService.findByName(ERole.ROLE_MODERATOR)).thenReturn(Optional.of(account));

        Optional<Role> found = roleService.findByName(ERole.ROLE_MODERATOR);

        AssertionsForInterfaceTypes.assertThat(found.get().getName()).isEqualTo(ERole.ROLE_MODERATOR);
    }

    @Test
    public void whenValidName_thenAccountShouldBeNotFound() {

        Role role = new Role(ERole.ROLE_MODERATOR);
        role.setId(1);

        when(roleService.findByName(ERole.ROLE_MODERATOR)).thenReturn(Optional.of(role));

        Optional<Role> found = roleService.findByName(ERole.ROLE_ADMIN);

        assertFalse(found.isPresent());
    }


}

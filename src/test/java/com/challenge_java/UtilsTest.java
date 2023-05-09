package com.challenge_java;

import com.challenge_java.model.entity.ERole;
import com.challenge_java.model.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class UtilsTest {
    public UtilsTest() {
    }

    public List<Role> getRoleList() {

        List<Role> roleList = new ArrayList<>();

        roleList.add(new Role(ERole.ROLE_USER));
        roleList.add(new Role(ERole.ROLE_ADMIN));
        roleList.add(new Role(ERole.ROLE_MODERATOR));

        return roleList;
    }
}

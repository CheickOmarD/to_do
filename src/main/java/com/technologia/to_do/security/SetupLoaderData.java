package com.technologia.to_do.security;


import com.technologia.to_do.enums.Statut;
import com.technologia.to_do.models.Role;
import com.technologia.to_do.models.Users;
import com.technologia.to_do.repository.RoleRepository;
import com.technologia.to_do.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class SetupLoaderData implements ApplicationListener<ContextRefreshedEvent> {

private final UsersRepository usersRepository;
private final RoleRepository roleRepository;
private final PasswordEncoder passwordEncoder;
private boolean alreadySetup = false;

public void onApplicationEvent(ContextRefreshedEvent event){
    if (alreadySetup) {
        return;
    }
    final Role superAdminRole = createRoleIfNotFound("SUPER_ADMIN");
    final Role userRole = createRoleIfNotFound("USERS");
    final Role adminRole = createRoleIfNotFound("ADMIN");
    final List<Role> adminRoles = new ArrayList<>(Arrays.asList(
            superAdminRole,userRole,adminRole
    ));
    createUsersIfNotFound(adminRoles );
    alreadySetup = true;
}

    void createUsersIfNotFound(List<Role> role){
    Users users = usersRepository.findByEmailAndStatut("technologia@gmail.com", Statut.ACTIVATED);
    if (users == null) {
        users = new Users();
        users.setFirstName("technologia");
        users.setLastName("corp");
        users.setEmail("technologia@gmail.com");
        users.setPassword(passwordEncoder.encode(
                "SoYeZlEcHaNgEmEnTqUeVoUsVoUlEzVoIrDaNsLeMoNdE2030"));
        users.setPhoneNumber("22391690592");
    }
    users.setRoles(role);
    usersRepository.save(users);
}

    Role createRoleIfNotFound(final String name){
    Role role = roleRepository.findByName(name);
    if (role == null) {
        role = new Role();
        role = roleRepository.save(role);
    }
    return role;
    }
}

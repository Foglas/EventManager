package cz.uhk.fim.projekt.EventManager.config;

import cz.uhk.fim.projekt.EventManager.Domain.Category;
import cz.uhk.fim.projekt.EventManager.Domain.Permission;
import cz.uhk.fim.projekt.EventManager.Domain.Role;
import cz.uhk.fim.projekt.EventManager.dao.CategoryRepo;
import cz.uhk.fim.projekt.EventManager.dao.PermissionRepo;
import cz.uhk.fim.projekt.EventManager.dao.RoleRepo;
import cz.uhk.fim.projekt.EventManager.enums.Permissions;
import cz.uhk.fim.projekt.EventManager.enums.Roles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class RepositoriesConfig {

    @Bean
    CommandLineRunner commandLineRunner(RoleRepo roleRepo, PermissionRepo permissionRepo){
        List<Role> listRole = new ArrayList<>();
        List<Permission> permissions = new ArrayList<>();

        return args -> {
                for (Roles role : Roles.values()) {
                    if (!roleRepo.existsRoleByType(role.name())) {
                        listRole.add(new Role(role.name()));
                    }
                }
                if (!listRole.isEmpty()) {
                    roleRepo.saveAll(listRole);
                }

            for (Permissions permission : Permissions.values()) {
                if (!permissionRepo.existsPermissionByDestricption(permission.name())) {
                    permissions.add(new Permission(permission));
                }
            }
            if (!listRole.isEmpty()) {
                roleRepo.saveAll(listRole);
            }

            if (!permissions.isEmpty()) {
                permissionRepo.saveAll(permissions);
            }
};
    }
}

package cz.uhk.fim.projekt.EventManager.config;

import cz.uhk.fim.projekt.EventManager.Domain.Category;
import cz.uhk.fim.projekt.EventManager.Domain.Role;
import cz.uhk.fim.projekt.EventManager.dao.CategoryRepo;
import cz.uhk.fim.projekt.EventManager.dao.RoleRepo;
import cz.uhk.fim.projekt.EventManager.enums.Roles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class RepositoriesConfig {

    @Bean
    CommandLineRunner commandLineRunner(CategoryRepo categoryRepo, RoleRepo roleRepo){
        List<Role> listRole = new ArrayList<>();
        return args -> {

            //TODO udelat obraceny config, kdyz se vymaze enum(role) v enum, pote vymazat roli v db
                for (Roles role : Roles.values()) {
                    if (!roleRepo.existsRoleByType(role.name())) {
                        listRole.add(new Role(role.name()));
                    }
                }
                if (!listRole.isEmpty()) {
                    roleRepo.saveAll(listRole);
                }

            Category jednicka = new Category("Malsdad", "jenad");
            categoryRepo.save(jednicka);
        };
    }
}

package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.Role;
import cz.uhk.fim.projekt.EventManager.service.RoleAndPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleAndPermissionController {

    private RoleAndPermissionService roleAndPermissionService;

    @Autowired
    public RoleAndPermissionController(RoleAndPermissionService roleAndPermissionService) {
        this.roleAndPermissionService = roleAndPermissionService;
    }


    @PostMapping("/auth/admin/role/save")
    public void saveRole(@RequestBody Role role){
        roleAndPermissionService.saveRole(role);
    }
}

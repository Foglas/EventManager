package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Role;
import cz.uhk.fim.projekt.EventManager.dao.PermissionRepo;
import cz.uhk.fim.projekt.EventManager.dao.RoleRepo;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleAndPermissionService {

  private RoleRepo roleRepo;
  private PermissionRepo permissionRepo;

  @Autowired
  public RoleAndPermissionService(
    RoleRepo roleRepo,
    PermissionRepo permissionRepo
  ) {
    this.roleRepo = roleRepo;
    this.permissionRepo = permissionRepo;
  }

  public void saveRole(Role role) {
    role.setUsers(new HashSet<>());
    roleRepo.save(role);
  }
}

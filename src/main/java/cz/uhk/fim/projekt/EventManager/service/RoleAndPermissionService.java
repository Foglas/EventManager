package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Role;
import cz.uhk.fim.projekt.EventManager.dao.PermissionRepo;
import cz.uhk.fim.projekt.EventManager.dao.RoleRepo;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Třída poskytující metody pro práci s rolemi
 */
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
  /**
   * metoda uloží novou roli
   * @param role objekt role k uložení
   */
  public void saveRole(Role role) {
    role.setUsers(new HashSet<>());
    roleRepo.save(role);
  }
}

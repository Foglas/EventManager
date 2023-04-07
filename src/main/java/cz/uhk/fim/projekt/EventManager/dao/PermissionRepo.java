package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Permission;
import cz.uhk.fim.projekt.EventManager.enums.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Long> {

    Optional<Permission> findByDestricption(String description);

    boolean existsPermissionByDestricption(String description);
}

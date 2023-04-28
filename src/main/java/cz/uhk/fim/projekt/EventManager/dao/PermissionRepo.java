package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Permission;
import cz.uhk.fim.projekt.EventManager.enums.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface poskytuje metody pro práci s tabulkou Permission
 */
@Repository
public interface PermissionRepo extends JpaRepository<Permission, Long> {
    /**
     * Metoda vrátí oprávnění podle popisu
     * @param description popis oprávnění
     */
    Optional<Permission> findByDestricption(String description);

    /**
     * Metoda vrátí boolean hodnotu, zda existuje oprávnění podle popisu
     * @param description popis oprávnění
     */
    boolean existsPermissionByDestricption(String description);
}

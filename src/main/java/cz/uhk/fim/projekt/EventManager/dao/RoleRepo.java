package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface poskytuje metody pro práci s tabulkou userRole
 */
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    /**
     * Metoda vrátí roli podle typu
     * @param type typ role
     */
    Optional<Role> findByType(String type);
    /**
     * Metoda vrátí boolean hodnotu, zda existuje role podle typu
     * @param type typ role
     */
    boolean existsRoleByType(String type);
}

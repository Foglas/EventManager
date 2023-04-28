package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface poskytuje metody pro práci s tabulkou userDetails
 */
public interface UserDetailsRepo extends JpaRepository<UserDetails, Long> {
}

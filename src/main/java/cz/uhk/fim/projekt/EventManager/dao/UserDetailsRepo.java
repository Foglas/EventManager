package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<UserDetails, Long> {
}

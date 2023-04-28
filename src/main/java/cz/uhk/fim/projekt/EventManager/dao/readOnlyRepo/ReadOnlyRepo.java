package cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import java.util.List;

/**
 * Interface definující repository bez možnosti zápisu
 * @param <T> Entita pohledu
 * @param <Z> Datový typ primárního klíče
 */
@NoRepositoryBean
public interface ReadOnlyRepo<T,Z> extends Repository<T,Z> {
    List<T> findAll();
}

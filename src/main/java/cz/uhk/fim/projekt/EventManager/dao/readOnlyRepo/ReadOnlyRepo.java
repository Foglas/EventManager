package cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo;

import cz.uhk.fim.projekt.EventManager.views.UserView;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;

@NoRepositoryBean
public interface ReadOnlyRepo<T,Z> extends Repository<T,Z> {
    List<T> findAll();
}

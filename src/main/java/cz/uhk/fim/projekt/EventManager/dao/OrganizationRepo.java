package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Event;
import cz.uhk.fim.projekt.EventManager.Domain.Organization;
import cz.uhk.fim.projekt.EventManager.views.EventView;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long> {
    @Query("SELECT isuserinorg(?1, ?2)")
    boolean isUserInOrganization(long userId, long organizationId);

    // new Event(pk_userid, description, name, time, endtime, coordinates, fk_addressid, fk_organizationid) FROM

    @Query(value = "SELECT eventsinorg(?1)", nativeQuery = true)
    List<Long> EventsInOrg(long id);

}


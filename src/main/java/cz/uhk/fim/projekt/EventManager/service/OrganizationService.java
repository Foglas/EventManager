package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Event;
import cz.uhk.fim.projekt.EventManager.Domain.Organization;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.EventRepo;
import cz.uhk.fim.projekt.EventManager.dao.OrganizationRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import cz.uhk.fim.projekt.EventManager.enums.Error;
import cz.uhk.fim.projekt.EventManager.service.serviceinf.OrganizationSerInf;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import cz.uhk.fim.projekt.EventManager.util.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Třída poskytuje metody pro obsluhu requestů týkajících se organizací
 */
@Service
public class OrganizationService implements OrganizationSerInf {

    private OrganizationRepo organizationRepo;
    private UserRepo userRepo;
    private EventRepo eventRepo;
    private JwtUtil jwtUtil;

    @Autowired
    public OrganizationService(
            OrganizationRepo organizationRepo,
            JwtUtil jwtUtil,
            UserRepo userRepo,
            EventRepo eventRepo
    ) {
        this.organizationRepo = organizationRepo;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.eventRepo = eventRepo;
    }
    /**
     * Metoda slouží k uložení organizace a přidání zakladatele do organizace.
     * @param request request, zjišťuje se z něho token
     * @param organization Objekt obsahující informace o organizaci
     */
    public void saveOrganization(
            Organization organization,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String email = jwtUtil.getEmailFromToken(token);
        User user = userRepo.findUserByEmailIgnoreCase(email);
        Set<User> users = new HashSet<>();
        users.add(user);
        organization.setUsers(users);
        organizationRepo.save(organization);
    }
    /**
     * Metoda vrátí seznam organizací uživatele
     * @param id ID uživatele, pro kterého hledáme organizace
     * @return seznam organizací
     */
    public List<Organization> getUserOrganization(Long id) {
        Optional<User> user = userRepo.findById(id);
        Set<Organization> organization = user.get().getOrganization();
        List<Organization> organizations = new ArrayList<>();
        organizations.addAll(organization);
        if (organizations.size() > 0) {
            System.out.println(organizations.get(0));
            return organizations;
        } else {
            return new ArrayList<>();
        }

    }
    /**
     * Metoda přidá uživatele do organizace
     * @param id ID organizace, do které přidáváme
     * @param body Objekt obsahující ID uživatele, kterého chceme přidat do organizace
     * @return vrací hlášku o úspěšném přidání, nebo chybovou hlášku, pokud dojde k chybě
     */
    public ResponseEntity<?> addUserToOrganization(Map<String, String> body, long id) {
        long userId = Long.parseLong(body.get("id"));
        User user = userRepo.findById(userId);
        if (user == null){
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "user not found");
        }
        Optional<Organization> organization = organizationRepo.findById(id);
        if (!organization.isPresent()){
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "organization not found");
        }

        Set<User> users = organization.get().getUsers();
        users.add(user);

        organization.get().setUsers(users);

        organizationRepo.save(organization.get());

        return ResponseHelper.successMessage("user added");
    }
    /**
     * Metoda vrátí seznam všech uživatelů organizace
     * @param id ID organizace, ze které získáváme uživatele
     * @return vrací seznam uživatelů
     */
    public List<User> getUsers(long id) {
        Optional<Organization> organization = organizationRepo.findById(id);
        Set<User> usersSet = organization.get().getUsers();
        List<User> userList = new ArrayList<>();
        userList.addAll(usersSet);
        return userList;
    }
    /**
     * Metoda slouží ke smazání organizace
     * @param id ID organizace, kterou chceme smazat
     * @param request request, zjišťuje se z něho token
     * @return vrací hlášku o úspěšném přidání, nebo chybovou hlášku, pokud dojde k chybě
     */
    @Transactional
    public ResponseEntity<?> deleteOrganization(long id, HttpServletRequest request) {
    User user = jwtUtil.getUserFromRequest(request, userRepo);
    if (!organizationRepo.isUserInOrganization(user.getId(), id)){
        return ResponseHelper.errorMessage(Error.NO_ACCESS.name(), "user cannot delete organization");
    }
    Optional<Organization> organization = organizationRepo.findById(id);
    if (!organization.isPresent()){
        return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "organization not found");
    }
    organizationRepo.delete(organization.get());
    return ResponseHelper.successMessage("Organization deleted");
    }
    /**
     * Metoda slouží k vyhledání událostí dané organizace
     * @param id ID organizace, jejiž události hledáme
     * @return vrací seznam událostí, nebo chybovou hlášku, pokud dojde k chybě
     */
    @Transactional
    public ResponseEntity<?> findEventByOrg(long id) {
        if (!organizationRepo.existsById(id)){
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "organization not found");
        }
        List<Long> eventsId = organizationRepo.EventsInOrg(id);
        List<Event> events = eventRepo.findAllById(eventsId);
        return ResponseEntity.ok().body(events);

    }
}

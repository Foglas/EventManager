package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.Organization;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.OrganizationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  Třída obsahující metody na příjímaní požadavků na url týkajících se akcí ohledně organizací.
 */
@RestController
@RequestMapping("/api")
public class OrganizationController {

    private OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * Přijme dotaz na url /api/user/{id}/organization. Parametry předá přislušné service vrstvě
     * pro zpracování. Slouží pro získání všech organizací ve kterých je uživatel členem. Id je id usera
     * @return http status, pokud je request neúspěšný nebo http status a seznam organizací uživatele,
     * pokud je úspěšný
     */
    @GetMapping(value = "user/{id}/organization")
    public List<Organization> getUserOrganizations(@PathVariable("id") Long id){
        return organizationService.getUserOrganization(id);
    }

    /**
     * Přijme dotaz na url /api/auth/organization/{id}/delete. Parametry předá přislušné service vrstvě
     * pro zpracování. Slouží pro smazání organizace. Organizaci může smazat jen její člen. Id je id usera.
     * Uživatel, který vyvolal dotaz se pozná podle tokenu v headeru. Vyžaduje autorizaci
     * @return http status a message
     */
    @DeleteMapping("/auth/organization/{id}/delete")
    public ResponseEntity<?> deleteOrganization(@PathVariable("id") long id, HttpServletRequest request){
        return organizationService.deleteOrganization(id, request);
    }

    /**
     * Přijme dotaz na url /api/organization/{id}/users. Parametry předá přislušné service vrstvě
     * pro zpracování. Slouží pro získání uživatelů dané organizace. Id je id organizace.
     * @return http status a list, pokud je request úspěšný nebo jen status pokud neúspěšný
     */
    @GetMapping("organization/{id}/users")
    public List<User> getOrganizationUsers(@PathVariable("id") long id){
        return organizationService.getUsers(id);
    }

    /**
     * Přijme dotaz na url /api/auth/organization/{id}/addUser. Vyžaduje autorizaci. Parametry předá
     * přislušné service vrstvě pro zpracování. Slouží pro přidání uživatele do dané organizace.
     * Id je id organizace. Informace o přidání se nachází v body.
     * @return http status a message
     */
    @PostMapping("/auth/organization/{id}/addUser")
    public ResponseEntity<?> addUserToOrganization(@RequestBody Map<String, String> body, @PathVariable("id") long id){
       return organizationService.addUserToOrganization(body, id);
    }

    /**
     * Přijme dotaz na url /api/auth/organization/save. Vyžaduje autorizaci. Parametry předá
     * přislušné service vrstvě pro zpracování. Slouží pro uložení organizace. Id je id organizace.
     * Uživatel se získa z headeru requestu. Informace o organizaci se nacházejí v body requestu.
     * @return
     */
    @PostMapping("/auth/organization/save")
    public void save(@RequestBody Organization organization, HttpServletRequest request){
        organizationService.saveOrganization(organization, request);
    }

    /**
     * Přijme dotaz na url /api/organization/{id}/events. Parametry předá přislušné service vrstvě
     * pro zpracování. Slouží pro získání eventů, které pořádá daná organizace. Id je id organizace.
     * @return http status a message
     */
    @GetMapping("organization/{id}/events")
    public ResponseEntity<?> findEventsByOrg(@PathVariable("id") long id){
     return    organizationService.findEventByOrg(id);
    }


}

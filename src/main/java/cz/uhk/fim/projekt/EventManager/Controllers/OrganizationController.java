package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.Organization;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.OrganizationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrganizationController {

    private OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping(value = "user/{id}/organization")
    public List<Organization> getUserOrganizations(@PathVariable("id") Long id){
        return organizationService.getOrganization(id);
    }

    @DeleteMapping("/auth/organization/{id}/delete")
    public ResponseEntity<?> deleteOrganization(@PathVariable("id") long id, HttpServletRequest request){
        return organizationService.deleteOrganization(id, request);
    }

    @GetMapping("organization/{id}/users")
    public List<User> getOrganizationUsers(@PathVariable("id") long id){
        return organizationService.getUsers(id);
    }

    @PostMapping("/auth/organization/{id}/addUser")
    public ResponseEntity<?> addUserToOrganization(@RequestBody Map<String, String> body, @PathVariable("id") long id){
       return organizationService.addUserToOrganization(body, id);
    }


    @PostMapping("/auth/organization/save")
    public void save(@RequestBody Organization organization, HttpServletRequest request){
        organizationService.saveOrganization(organization, request);
    }


}

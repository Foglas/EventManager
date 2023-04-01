package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Organization;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.OrganizationRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import cz.uhk.fim.projekt.EventManager.service.serviceinf.OrganizationSerInf;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService implements OrganizationSerInf {

    private OrganizationRepo organizationRepo;
    private UserRepo userRepo;
    private JwtUtil jwtUtil;

    @Autowired
    public OrganizationService(
            OrganizationRepo organizationRepo,
            JwtUtil jwtUtil,
            UserRepo userRepo
    ) {
        this.organizationRepo = organizationRepo;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

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

    public List<Organization> getOrganization(Long id) {
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
}

package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Organization;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.OrganizationRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import cz.uhk.fim.projekt.EventManager.service.serviceinf.OrganizationSerInf;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService implements OrganizationSerInf {

    private OrganizationRepo organizationRepo;
    private UserRepo userRepo;
    private JwtUtil jwtUtil;

    @Autowired
    public OrganizationService(OrganizationRepo organizationRepo, JwtUtil jwtUtil, UserRepo userRepo) {
        this.organizationRepo = organizationRepo;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    public void saveOrganization(Organization organization, HttpServletRequest request){
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtUtil.getUsernameFromToken(token);
        User user = userRepo.findUserByUsername(username);
        Organization organization1 = organization.addUser(user);
        organizationRepo.save(organization1);
    }

}

package cz.uhk.fim.projekt.EventManager.service.serviceinf;

import cz.uhk.fim.projekt.EventManager.Domain.User;

public interface UserServiceInf {
    User findUserByUserName(String username);
}

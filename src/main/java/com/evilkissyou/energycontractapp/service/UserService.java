package com.evilkissyou.energycontractapp.service;

import com.evilkissyou.energycontractapp.entity.User;


public interface UserService {
    User getById(int id);
    User getByEmail(String email);
}

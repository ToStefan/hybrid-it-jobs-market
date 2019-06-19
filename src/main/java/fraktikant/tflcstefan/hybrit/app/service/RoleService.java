package fraktikant.tflcstefan.hybrit.app.service;

import fraktikant.tflcstefan.hybrit.app.entity.Role;

public interface RoleService {

    Role findByName(Enum roleName);
}

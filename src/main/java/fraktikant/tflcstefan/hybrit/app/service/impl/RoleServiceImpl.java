package fraktikant.tflcstefan.hybrit.app.service.impl;

import fraktikant.tflcstefan.hybrit.app.entity.Role;
import fraktikant.tflcstefan.hybrit.app.repository.RoleRepository;
import fraktikant.tflcstefan.hybrit.app.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Role findByName(Enum roleName) {
        return roleRepository.findByName(roleName);
    }
}

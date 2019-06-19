package fraktikant.tflcstefan.hybrit.app.repository;

import fraktikant.tflcstefan.hybrit.app.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

    Role findByName(Enum roleName);
}

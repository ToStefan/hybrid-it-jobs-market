package fraktikant.tflcstefan.hybrit.app.repository;

import fraktikant.tflcstefan.hybrit.app.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);
    Boolean existsByUsername(String username);

    @Query(value = "SELECT * FROM USERS WHERE task_id is not null", nativeQuery = true)
    List<User> findAllWhereTaskIdNotNull();
}
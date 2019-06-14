package fraktikant.tflcstefan.hybrit.app.repository;

import fraktikant.tflcstefan.hybrit.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
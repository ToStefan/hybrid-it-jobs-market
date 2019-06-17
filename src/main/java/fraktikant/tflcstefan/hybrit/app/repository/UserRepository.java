package fraktikant.tflcstefan.hybrit.app.repository;

import fraktikant.tflcstefan.hybrit.app.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
package codemark.testservice.repository;

import codemark.testservice.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
    List<UserEntity> findAll();
}

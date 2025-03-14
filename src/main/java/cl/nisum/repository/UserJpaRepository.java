package cl.nisum.repository;

import cl.nisum.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserJpaRepository extends JpaRepository<UserModel,Long> {

    UserModel save(UserModel entity);

    UserModel saveAndFlush(UserModel entity);

    Optional<UserModel> findById(Long id);

    UserModel findByEmail(String email);
}

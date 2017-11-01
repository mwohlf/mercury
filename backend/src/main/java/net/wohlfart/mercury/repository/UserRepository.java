package net.wohlfart.mercury.repository;

import net.wohlfart.mercury.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByName(String name);


    User findById(Long id);

    Page<User> findAllBy(Pageable pageable);

}

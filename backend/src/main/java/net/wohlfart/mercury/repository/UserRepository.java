package net.wohlfart.mercury.repository;

import net.wohlfart.mercury.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Subject, Long> {

    Subject findByEmail(String email);

    Subject findByUsername(String name);

    Subject findById(Long id);

    Page<Subject> findAllBy(Pageable pageable);

}

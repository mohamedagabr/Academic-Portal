package com.academic.portal.user.repository;

import com.academic.portal.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    @EntityGraph(attributePaths = {"refreshTokens"})
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"refreshTokens"})
    Optional<User> findById(Integer id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

}

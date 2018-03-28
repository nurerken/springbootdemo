package kz.guestbook.components.User.repository;

import kz.guestbook.components.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("select u from User u where u.id = :userId and u.active = true")
    public User findById(@Param("userId") Long userId);

    @Query("select u from User u where u.email = :email and u.active = true")
    public User findActiveByEmail(@Param("email") String email);

    @Query("select u from User u where u.active = true and (lower(u.email) like concat('%', lower(:searchString), '%') or lower(u.firstName) like concat('%', lower(:searchString), '%') or lower(u.lastName) like concat('%', lower(:searchString), '%')) order by id")
    public List<User> findByEmailOrFirstNameOrLastName(@Param("searchString") String searchString);

    @Modifying
    @Transactional
    @Query("update User set active = :active where id = :id")
    public Integer setActive(@Param("id") Long id,@Param("active") boolean active);
}

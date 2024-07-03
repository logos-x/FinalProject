package courseproject.library.repository;

import courseproject.library.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}

package courseproject.library.repository;

import courseproject.library.dto.BrandDto;
import courseproject.library.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBrandRepository extends JpaRepository<Brand, Long> {
    @Query(value = "update Brand set name = ?1 where id = ?2")
    Brand update(String name, Long id);

    @Query(value = "select * from categories where is_activated = true", nativeQuery = true)
    List<Brand> findAllByActivatedTrue();

}

package courseproject.library.service;

import courseproject.library.entity.Brand;
import courseproject.library.repository.IBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    @Autowired
    private IBrandRepository brandRepository;

    public Brand save(Brand category) {
        Brand categorySave = new Brand();
        categorySave.setName(category.getName());
        categorySave.setActivated(true);
        categorySave.setDeleted(false);
        return brandRepository.save(categorySave);

    }

    public Brand update(Brand category) {
        Brand categoryUpdate = brandRepository.getReferenceById(category.getId());
        categoryUpdate.setName(category.getName());
        return brandRepository.save(categoryUpdate);
    }

    public List<Brand> findAllByActivatedTrue() {
        return brandRepository.findAllByActivatedTrue();
    }

    public List<Brand> findALl() {
        return brandRepository.findAll();
    }

    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id);
    }

    public void deleteById(Long id) {
        Brand category = brandRepository.getById(id);
        category.setActivated(false);
        category.setDeleted(true);
        brandRepository.save(category);
    }

    public void enableById(Long id) {
        Brand category = brandRepository.getById(id);
        category.setActivated(true);
        category.setDeleted(false);
        brandRepository.save(category);
    }

//    public List<CategoryDto> getCategoriesAndSize() {
//        List<CategoryDto> categories = brandRepository.getCategoriesAndSize();
//        return categories;
//    }

}

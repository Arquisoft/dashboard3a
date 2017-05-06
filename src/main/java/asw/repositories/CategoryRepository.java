package asw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import asw.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}

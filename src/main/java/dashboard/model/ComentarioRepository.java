package dashboard.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
	@Query("select c from Comentario c where c.sugerencia = ?1")
	List<Comentario> findBySugerencia(Sugerencia sugerencia);
}

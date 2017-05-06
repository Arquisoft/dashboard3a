package asw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import asw.model.Comment;
import asw.model.Suggestion;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query("select c from Comment c where c.suggestion = ?1")
	List<Comment> findBySugerencia(Suggestion sugerencia);
}

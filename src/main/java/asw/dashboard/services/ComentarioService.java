package asw.dashboard.services;

import java.util.List;

import asw.model.Comment;
import asw.model.Suggestion;

public interface ComentarioService {
	List<Comment> findAll();
	List<Comment> findBySugerencia(Suggestion sugerencia);
}

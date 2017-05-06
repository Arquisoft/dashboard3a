package asw.dashboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.model.Comment;
import asw.model.Suggestion;
import asw.repositories.CommentRepository;

@Service
public class ComentarioServiceImpl implements ComentarioService {

	@Autowired
	private CommentRepository comentarioRepository;
	
	@Override
	public List<Comment> findAll() {
		return comentarioRepository.findAll();
	}

	@Override
	public List<Comment> findBySugerencia(Suggestion sugerencia) {
		return comentarioRepository.findBySugerencia(sugerencia);
	}
}

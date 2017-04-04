package dashboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dashboard.model.Comentario;
import dashboard.model.ComentarioRepository;
import dashboard.model.Sugerencia;

@Service
public class ComentarioServiceImpl implements ComentarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Override
	public List<Comentario> findAll() {
		return comentarioRepository.findAll();
	}

	@Override
	public List<Comentario> findBySugerencia(Sugerencia sugerencia) {
		return comentarioRepository.findBySugerencia(sugerencia);
	}
}

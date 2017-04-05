package dashboard.services;

import java.util.List;

import model.Comentario;
import model.Sugerencia;

public interface ComentarioService {
	List<Comentario> findAll();
	
	List<Comentario> findBySugerencia(Sugerencia sugerencia);
}

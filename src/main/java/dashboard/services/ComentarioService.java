package dashboard.services;

import java.util.List;

import dashboard.model.Comentario;
import dashboard.model.Sugerencia;

public interface ComentarioService {
	List<Comentario> findAll();
	
	List<Comentario> findBySugerencia(Sugerencia sugerencia);
}

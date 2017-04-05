package asw.dashboard.services;

import java.util.List;

import asw.model.Comentario;
import asw.model.Sugerencia;

public interface ComentarioService {
	List<Comentario> findAll();
	
	List<Comentario> findBySugerencia(Sugerencia sugerencia);
}

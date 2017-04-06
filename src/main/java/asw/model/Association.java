package asw.model;

public class Association {

	public static class Asignar {

		public static void link(Usuario usuario, Ciudadano ciudadano) {
			usuario._setCiudadano(ciudadano);
			ciudadano._setUsuario(usuario);
		}

		public static void unlink(Usuario usuario, Ciudadano ciudadano) {
			ciudadano._setUsuario(null);
			usuario._setCiudadano(null);
		}
	}
	
	public static class AsignarSugerencia {
		
		public static void link(Usuario usuario, Sugerencia sugerencia) {
			usuario._addSugerencia(sugerencia);
			sugerencia._setUsuario(usuario);
		}
		
		public static void unlink(Usuario usuario, Sugerencia sugerencia) {
			usuario._removeSugerencia(sugerencia);
			sugerencia._setUsuario(null);
		}
	}
	
	public static class AsignarComentario {
		
		public static void link(Comentario comentario, Sugerencia sugerencia, Usuario usuario){
			usuario._addComentario(comentario);
			sugerencia._addComentario(comentario);
			comentario._setSugerencia(sugerencia);
			comentario._setUsuario(usuario);
		}
		
		public static void unlink(Comentario comentario, Sugerencia sugerencia, Usuario usuario){
			usuario._removeComentario(comentario);
			usuario._removeSugerencia(sugerencia);
			comentario._setSugerencia(null);
			comentario._setUsuario(null);
		}
	}
	
	public static class AsignarCategoria {
		
		public static void link(Categoria categoria, Sugerencia sugerencia) {
			categoria.addSugerencia(sugerencia);
			sugerencia._setCategoria(categoria);
		}
		
		public static void unlink(Categoria categoria, Sugerencia sugerencia) {
			categoria._getSugerencias().remove(sugerencia);
			sugerencia._setCategoria(null);
		}
		
	}
}

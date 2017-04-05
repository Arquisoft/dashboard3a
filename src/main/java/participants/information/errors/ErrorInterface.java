package participants.information.errors;

/**
 * Sólo sirve para poder recoger las excepciones que se puedan
 * recoger las excepciones que se producen en la respuesta
 * 
 */
public abstract class ErrorInterface extends RuntimeException
{
	protected static final long serialVersionUID = 1L;	
	
	public abstract String getJSONError();	
	public abstract String getStringError();
}
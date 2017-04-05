package participants.information.citizen;

/**
 * Representa la información que irá en el JSON cuando se 
 * piden los datos del usuario
 * 
 * @author UO247242
 *  
 */
public class CitizenInformationRequest {
	
	private String login;
	private String password;
	
	public CitizenInformationRequest(String login, String password) {	

		this.login = login;
		this.password = password;
	}
	
	public CitizenInformationRequest() {}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	
}

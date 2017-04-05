package participants.information.errors;

//
public class CitizenNotFoundError extends ErrorInterface {

	private static final long serialVersionUID = 1L;

	@Override
	public String getJSONError() {
		return "{\"reason\": \"404: Not found\"}";
	}

	@Override
	public String getStringError() {
		return "404: Not found";
	}
}

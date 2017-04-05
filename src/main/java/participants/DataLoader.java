package participants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import model.CitizenRepository;
import model.Ciudadano;
import model.Usuario;

@Component
public class DataLoader implements ApplicationRunner {

	private static final String DEFAULT_USERS_FILE_PATH = "/defaultUsers.txt";
	private CitizenRepository citizenRepository;
	
	@Autowired
	public DataLoader(CitizenRepository citizenRepository) {
		this.citizenRepository = citizenRepository;
	}
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		BufferedReader bfReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(DEFAULT_USERS_FILE_PATH)));
		
	/*	while (bfReader.ready()) {
			String line = bfReader.readLine();
			String[] lineParts = line.split(";");
			citizenRepository.save(new Ciudadano(lineParts[0], 
					lineParts[1], 
					lineParts[2], 
					dateFormat.parse(lineParts[3]), 
					lineParts[4], 
					lineParts[5], 
					lineParts[6], 
					new Usuario(lineParts[7], lineParts[8])));
		}
		
		bfReader.close();*/
	}
}

package app;

import app.utilities.ConsoleAppUtilities;
import dal.gateway.MapGateway;
import dto.AllDTOFactory;
import lombok.extern.log4j.Log4j2;
import mapper.Mapper;

@Log4j2
public class ConsoleApp {

	public static void main(String[] args) {
		log.info("Launching Console application.");
		
		ConsoleAppUtilities.wellcomeScreen();
		
		/*MapGateway mg = new MapGateway();
		
		mg.insert("Favela");
		mg.insert("Bank");
		mg.insert("Chalet");
		
		System.out.println(mg.findAll().toString());
		System.out.println(mg.findById(3)); //chalet??
		
		//mg.update(AllDTOFactory.createMap(2, "fsdfsdf"));
		System.out.println(Mapper.toDomain(mg.update(AllDTOFactory.createMap(3, "fsdfsdf"))).toString());
		System.out.println(mg.findAll().toString());
		
		mg.delete(2);
		System.out.println(mg.findAll().toString());*/
	}
	
}
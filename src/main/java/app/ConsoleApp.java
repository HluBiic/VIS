package app;

import java.sql.SQLException;

import app.utilities.ConsoleAppUtilities;
import dal.UnitOfWork;
import dal.gateway.MapGateway;
import dto.AllDTOFactory;
import lombok.extern.log4j.Log4j2;
import mapper.Mapper;

@Log4j2
public class ConsoleApp {

	public static void main(String[] args) throws SQLException {
		log.info("Launching Console application.");
		
		ConsoleAppUtilities.uow = new UnitOfWork();
		ConsoleAppUtilities.uow.begin();
		
		ConsoleAppUtilities.wellcomeScreen();
	}
}
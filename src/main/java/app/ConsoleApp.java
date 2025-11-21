package app;

import java.sql.SQLException;

import app.utilities.ConsoleAppUtilities;
import dal.UnitOfWork;
import dal.gateway.MapGateway;
import dto.AllDTOFactory;
import lombok.extern.log4j.Log4j2;
import mapper.Mapper;

/**
 * Main entry point for the console application. Initializes the logging 
 * system and the Unit of Work for database transaction management.
 */
@Log4j2
public class ConsoleApp {

	/**
	 * The main method that launches the console application. Initializes the Unit 
	 * of Work, starts a transaction, and displays the welcome screen.
	 */
	public static void main(String[] args) throws SQLException {
		log.info("Launching Console application.");
		
		ConsoleAppUtilities.uow = new UnitOfWork();
		ConsoleAppUtilities.uow.begin();
		
		ConsoleAppUtilities.wellcomeScreen();
	}
}
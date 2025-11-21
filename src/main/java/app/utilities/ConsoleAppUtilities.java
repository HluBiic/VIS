package app.utilities;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import dal.UnitOfWork;
import dal.repo.MapRepository;
import dto.MapDTO;
import dto.MatchDTO;
import lombok.extern.log4j.Log4j2;
import model.Map;
import model.Match;
import model.Team;
import model.Tournament;
import services.MapService;
import services.MatchService;
import services.TeamService;
import services.TournamentService;

/**
 * Provides utility methods to handle the console user interface flow, user input,
 * and interactions with application services for managing matches.
 */
@Log4j2
public class ConsoleAppUtilities {	
	private static Scanner scanner = new Scanner(System.in);
	private static String caster = "undefined";
	public static UnitOfWork uow;
	
	private static MatchService matchService = new MatchService();
	static {
	    matchService.addListener(Logger.getInstance());
	}
	
	private static TournamentService tourService = new TournamentService();
	private static MapService mapService = new MapService();
	private static TeamService teamService = new TeamService();
	
	/**
	 * Prints a message indicating an invalid menu choice.
	 */
	private static void invalidChoice() {
		System.out.println("Neplatná voľba. Skúste znova.");
	}
	
	/**
	 * Clears the console output for a cleaner screen display.
	 */
	private static void clearConsole() {
	    //System.out.print("\033[H\033[2J");
	    //System.out.flush();
	    for (int i = 0; i < 30; i++) {
	        System.out.println();
	    }
	}
	
	/**
	 * Displays the main menu for match management operations (Create, Edit, Delete)
	 * for a signed caster. Initalizes maps, tournaments and teams.
	 */
	private static void matches() {
		mapService.initMaps();
		tourService.initTournaments();
		teamService.initTeams();
		
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("        Správa zápasov - Prihlásený: " + caster);
		System.out.println("=====================================================\n");
		
		System.out.println("Dostupné operácie:");
		System.out.println("[ 1 ] Vytvoriť nový zápas");
		System.out.println("[ 2 ] Editovať existujúci zápas");
		System.out.println("[ 3 ] Vymazať existujúci zápas");
		System.out.println("[ 4 ] Odhlásiť sa");
		
		System.out.print("\nProsím zadajte požadovanú voľbu (1 - 4): ");
		
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
        case 1:
        	newMatchForm();
        	break;
        case 2:
        	editExistingMatch();
        	break;
        case 3:
        	deleteExistingMatch();
        	break;
        case 4:
        	wellcomeScreen();
        	break;
        default:
        	invalidChoice();
        	break;
        }
	}
	
	/**
	 * Presents the form for creating a new match, handles user input, validation, and saving.
	 */
	private static void newMatchForm() {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("                Vytvorenie nového zápasu");
		System.out.println("=====================================================\n");
		
		System.out.println("- Povinné polia sú označené hviezdičkou (*).");
		System.out.println("- Skóre musí byť vo formáte M-N (napr. 7-5).");
		
		System.out.print("\n* 1. Zadajte turnaj: ");
		String tourName = scanner.nextLine().trim();
		System.out.print("\n* 2. Zadajte tím A: ");
		String teamAName = scanner.nextLine().trim();
		System.out.print("\n* 3. Zadajte tím B: ");
		String teamBName = scanner.nextLine().trim();
		System.out.print("\n* 4. Zadajte skóre: ");
		String score = scanner.nextLine().trim();
		System.out.print("\n* 5. Zadajte názov mapy: ");
		String mapName = scanner.nextLine().trim();
		
		System.out.println("\nPotvrdiť uloženie? [y/n]: ");
		String choice = scanner.nextLine().trim();
        
        switch (choice) {
        case "y":
        	String tourNameValidationResult = Validator.validateTournamentName(tourName);
        	String teamANameValidationResult = Validator.validateTeamAName(teamAName);
        	String teamBNameValidationResult = Validator.validateTeamBName(teamBName);
        	String teamVsTeamValidationResult = Validator.validateTeamVsTeam(teamAName, teamBName);
        	String mapNameValidationResult = Validator.validateMapName(mapName);
        	String matchScoreValidationResult = Validator.validateMatchScore(score);
        	
        	if (tourNameValidationResult != null) {
        		validationErrorMessage(tourNameValidationResult);
        	} else if (teamANameValidationResult != null) {
        		validationErrorMessage(teamANameValidationResult);
        	} else if (teamBNameValidationResult != null) {
        		validationErrorMessage(teamBNameValidationResult);
        	} else if (teamVsTeamValidationResult != null) {
        		validationErrorMessage(teamVsTeamValidationResult);
        	} else if (matchScoreValidationResult != null) {
        		validationErrorMessage(matchScoreValidationResult);
        	} else if (mapNameValidationResult != null) {
        		validationErrorMessage( mapNameValidationResult);
        	} else {
        		Map map = mapService.getMapByName(mapName);
        		Tournament tour = tourService.getTourByName(tourName);
        		Team teamA = teamService.getTeamByName(teamAName);
        		Team teamB = teamService.getTeamByName(teamBName);
        		Match m = matchService.newMatch(tour.getId(), teamA.getId(), teamB.getId(), map.getId(), score, caster);
        		System.out.println(matchService.getAllMatches());
        		saveSuccessMessage(m.getId());
        	}
        	break;
        case "n":
        	matches();
        	break;
        default:
        	invalidChoice();
        	break;
        }
	}
	
	/**
	 * Displays a list of existing matches and prompts the user to select one for editing.
	 */
	private static void editExistingMatch() {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("               Editácia existujúceho zápasu");
		System.out.println("=====================================================\n");
		
		System.out.println("Aktuálne zápasy:");
		List<Match> matches = matchService.getAllMatches();
		for (Match match : matches) {
			System.out.println(matchService.getMatchInfo(match.getId()));
		}

		System.out.print("\nProsím zadajte číslo zápasu ktorý chcecete editovať: ");
		int choice = scanner.nextInt();
		scanner.nextLine();
        
		updateForm(choice);
	}
	
	/**
	 * Presents the form to update an existing match, handles user input, validation, and updating.
	 */
	private static void updateForm(int matchID) {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("          Editácia zápasu [ " + matchID + " ]");
		System.out.println("=====================================================\n");
		
		System.out.println("- Povinné polia sú označené hviezdičkou (*).");
		System.out.println("- Skóre musí byť vo formáte M-N (napr. 7-5).");
		
		System.out.print("\n* 1. Zadajte turnaj: ");
		String tourName = scanner.nextLine().trim();
		System.out.print("\n* 2. Zadajte tím A: ");
		String teamAName = scanner.nextLine().trim();
		System.out.print("\n* 3. Zadajte tím B: ");
		String teamBName = scanner.nextLine().trim();
		System.out.print("\n* 4. Zadajte skóre: ");
		String score = scanner.nextLine().trim();
		System.out.print("\n* 5. Zadajte názov mapy: ");
		String mapName = scanner.nextLine().trim();
		
		System.out.println("\nPotvrdiť uloženie? [y/n]: ");
		String choice = scanner.nextLine().trim();
		
        switch (choice) {
        case "y":
        	String tourNameValidationResult = Validator.validateTournamentName(tourName);
        	String teamANameValidationResult = Validator.validateTeamAName(teamAName);
        	String teamBNameValidationResult = Validator.validateTeamBName(teamBName);
        	String teamVsTeamValidationResult = Validator.validateTeamVsTeam(teamAName, teamBName);
        	String mapNameValidationResult = Validator.validateMapName(mapName);
        	String matchScoreValidationResult = Validator.validateMatchScore(score);
        	
        	if (tourNameValidationResult != null) {
        		validationErrorMessageAfterUpdate(tourNameValidationResult, matchID);
        	} else if (teamANameValidationResult != null) {
        		validationErrorMessageAfterUpdate(teamANameValidationResult, matchID);
        	} else if (teamBNameValidationResult != null) {
        		validationErrorMessageAfterUpdate(teamBNameValidationResult, matchID);
        	} else if (teamVsTeamValidationResult != null) {
        		validationErrorMessageAfterUpdate(teamVsTeamValidationResult, matchID);
        	} else if (matchScoreValidationResult != null) {
        		validationErrorMessageAfterUpdate(matchScoreValidationResult, matchID);
        	} else if (mapNameValidationResult != null) {
        		validationErrorMessageAfterUpdate( mapNameValidationResult, matchID);
        	} else {
        		Map map = mapService.getMapByName(mapName);
        		Tournament tour = tourService.getTourByName(tourName);
        		Team teamA = teamService.getTeamByName(teamAName);
        		Team teamB = teamService.getTeamByName(teamBName);
        		Match m = matchService.updateMatch(matchID, tour.getId(), teamA.getId(), teamB.getId(), map.getId(), score, caster);
        		System.out.println(matchService.getAllMatches());
        		updateSuccessMessage(m.getId());
        	}
        	break;
        case "n":
        	matches();
        	break;
        default:
        	invalidChoice();
        	break;
        }
	}
	
	/**
	 * Displays a success message after a match has been successfully updated.
	 */
	private static void updateSuccessMessage(int id) {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("                 Uloženie opraveného zápasu");
		System.out.println("=====================================================\n");
		
		System.out.println("Záznam o zápase bol úspešne aktualizovaný. (ID zápasu: " + id + ")");
		System.out.println("Info o aktualizácií bolo zapísané do logu.");
		
		System.out.println("\nPokračovať:");
		System.out.println("[ 1 ] Návrat do menu Správa zápasov");
		System.out.println("[ 2 ] Odhlásiť sa");
		
		System.out.print("\nProsím zadajte požadovanú voľbu (1 - 2): ");
		
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
        case 1:
        	matches();
        	break;
        case 2:
        	wellcomeScreen();
        	break;
        default:
        	invalidChoice();
        	break;
        }
	}
	
	/**
	 * Displays a success message after a new match has been successfully saved.
	 */
	private static void saveSuccessMessage(int id) {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("                  Uloženie nového zápasu");
		System.out.println("=====================================================\n");
		
		System.out.println("Záznam o zápase bol úspešne uložený. (ID zápasu: " + id + ")");
		System.out.println("Info o vložení bolo zapísané do logu.");
		
		System.out.println("\nPokračovať:");
		System.out.println("[ 1 ] Vytvoriť ďalší zápas");
		System.out.println("[ 2 ] Návrat do menu Správa zápasov");
		System.out.println("[ 3 ] Odhlásiť sa");
		
		System.out.print("\nProsím zadajte požadovanú voľbu (1 - 3): ");
		
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
        case 1:
        	newMatchForm();
        	break;
        case 2:
        	matches();
        	break;
        case 3:
        	wellcomeScreen();
        	break;
        default:
        	invalidChoice();
        	break;
        }
	}
	

	/**
	 * Displays validation errors during match creation and prompts the user to retry or cancel.
	 */
	private static void validationErrorMessage(String errorMessage) {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("                  CHYBA VALIDÁCIE");
		System.out.println("=====================================================\n");

		System.out.println(errorMessage);

		System.out.println("\nZadajte voľbu:");
		System.out.println("[ 1 ] Opraviť chyby a skúsiť uložiť znova");
		System.out.println("[ 2 ] Zrušiť operáciu a návrat do menu Správa Zápasov");
		
		System.out.print("\nProsím zadajte požadovanú voľbu (1 - 2): ");
		
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
        case 1:
        	newMatchForm();
        	break;
        case 2:
        	matches();
        	break;
        default:
        	invalidChoice();
        	break;
        }
	}
	
	/**
	 * Displays validation errors during match update and prompts the user to retry or cancel.
	 */
	private static void validationErrorMessageAfterUpdate(String errorMessage, int matchId) {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("                  CHYBA VALIDÁCIE");
		System.out.println("=====================================================\n");

		System.out.println(errorMessage);
		
		System.out.println("\nZadajte voľbu:");
		System.out.println("[ 1 ] Opraviť chyby a skúsiť uložiť znova");
		System.out.println("[ 2 ] Zrušiť operáciu a návrat do menu Správa Zápasov");
		
		System.out.print("\nProsím zadajte požadovanú voľbu (1 - 2): ");
		
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
        case 1:
        	updateForm(matchId);
        	break;
        case 2:
        	matches();
        	break;
        default:
        	invalidChoice();
        	break;
        }
	}
	
	/**
	 * Displays a list of existing matches and prompts the user to select one for deletion.
	 */
	private static void deleteExistingMatch() {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("               Zmazanie existujúceho zápasu");
		System.out.println("=====================================================\n");
		
		System.out.println("Aktuálne zápasy:");
		List<Match> matches = matchService.getAllMatches();
		for (Match match : matches) {
			System.out.println(matchService.getMatchInfo(match.getId()));
		}

		System.out.print("\nProsím zadajte číslo zápasu ktorý chcece vymazat: ");
		int choice = scanner.nextInt();
		scanner.nextLine();
        
		deleteConfirmation(choice);
	}
	
	/**
	 * Requests final confirmation from the user before deleting a match.
	 */
	private static void deleteConfirmation(int matchID) {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("      Zmazanie existujúceho zápasu - potvrdenie");
		System.out.println("=====================================================\n");
		
		System.out.println("Skutočne chcete vymazať nasledujúci záznam o zápase:\n");
		System.out.println(matchService.getMatchInfo(matchID));
		
		System.out.print("\nPotvrdiť vymazanie zápasu [y/n]: ");
		String choice = scanner.nextLine().trim();
        
        switch (choice) {
        case "y":
        	deleteSuccessMessage(matchID);
        	break;
        case "n":
        	matches();
        	break;
        default:
        	invalidChoice();
        	break;
        }
	}
	
	/**
	 * Executes the match deletion and displays a success message.
	 */
	private static void deleteSuccessMessage(int matchID) {
		Match m = matchService.getMatchById(matchID);
		matchService.deleteMatch(m, caster);
		
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("                  Zápas úspešne zmazaný");
		System.out.println("=====================================================\n");
		
		System.out.println("Záznam o zápase bol úspešne zmazaný. ");
		System.out.println("Info o zmazaní bolo zapísané do logu.");
		
		System.out.println("\nPokračovať:");
		System.out.println("[ 1 ] Návrat do menu Správa zápasov");
		System.out.println("[ 2 ] Odhlásiť sa");
		
		System.out.print("\nProsím zadajte požadovanú voľbu (1 - 2): ");
		
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
        case 1:
        	matches();
        	break;
        case 2:
        	wellcomeScreen();
        	break;
        default:
        	invalidChoice();
        	break;
        }
	}
	
	/**
	 * Displays the initial welcome screen, handles main menu navigation, and starts the application loop.
	 */
	public static void wellcomeScreen() {
		while(true) {
			clearConsole();
			System.out.println("\n=====================================================");
			System.out.println("             R6 správca zápasov");
			System.out.println("=====================================================\n");
			
			System.out.println("DNES JE: " + LocalDate.now().getDayOfMonth() + "." + LocalDate.now().getMonthValue() + "." + LocalDate.now().getYear());
			
			System.out.println("\nDostupné operácie:");
			System.out.println("[ 1 ] Prihlásiť sa do systému");
			System.out.println("[ 2 ] O systéme");
			System.out.println("[ 3 ] Ukončiť");
			
			System.out.print("\nProsím zadajte požadovanú voľbu (1 - 3): ");
			
	        int choice = scanner.nextInt();
	        scanner.nextLine();
	        
	        switch (choice) {
	        case 1:
	        	loginScreen();
	        	break;
	        case 2:
	        	aboutScreen();
	        	break;
	        case 3:
	        	endScreen();
	        	break;
	        default:
	        	invalidChoice();
	        	break;
	        }
		}
	}
	
	/**
	 * Prompts the user to enter their caster name and proceeds to the match management menu.
	 */
	private static void loginScreen() {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("        R6 správca zápasov - Prihlásenie");
		System.out.println("=====================================================\n");
		
		System.out.print("\nMeno: ");
		caster = scanner.nextLine().trim();
		
		matches();
	}
	
	/**
	 * Displays brief information about the console application (for now login and subject).
	 */
	private static void aboutScreen() {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("          R6 správca zápasov - O systéme");
		System.out.println("=====================================================\n");
		
		System.out.println("LOGIN: HLU0035");
		System.out.println("VIS projekt");
		
		System.out.print("\nNávrat do hlavného menu [y/n]: ");
		
		String choice = scanner.nextLine().trim();
        
        switch (choice) {
        case "y":
        	wellcomeScreen();
        case "n":
        	aboutScreen();
        	break;
        default:
        	invalidChoice();
        	break;
        }
	}
	
	/**
	 * Displays the end screen, logs the closing, shuts down the UnitOfWork which closes 
	 * database connection, and terminates the application.
	 */
	private static void endScreen() {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("                       KONIEC");
		System.out.println("=====================================================\n");
		log.info("Closing Console application.");
		ConsoleAppUtilities.uow.end();
		System.exit(0);
	}
}
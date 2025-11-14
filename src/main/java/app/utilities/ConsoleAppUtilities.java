package app.utilities;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import dal.repo.MapRepository;
import dto.MapDTO;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConsoleAppUtilities {	
	private static Scanner scanner = new Scanner(System.in);
	private static String caster = "undefined";
	
	private static void invalidChoice() {
		System.out.println("Neplatná voľba. Skúste znova.");
	}
	
	private static void clearConsole() {
	    System.out.print("\033[H\033[2J");
	    System.out.flush();
	    /*for (int i = 0; i < 50; i++) {
	        System.out.println();
	    }*/
	}
	
	private static void matches() {
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
        	//endScreen();
        	break;
        case 4:
        	wellcomeScreen();
        	break;
        default:
        	invalidChoice();
        	break;
        }
		
	}
	
	//TODO dodat zvysne polozky do formulara ked budu aj v DB
	private static void newMatchForm() {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("                Vytvorenie nového zápasu");
		System.out.println("=====================================================\n");
		
		System.out.println("- Povinné polia sú označené hviezdičkou (*).");
		System.out.println("- Skóre musí byť vo formáte M-N (napr. 7-5).");
		
		System.out.print("\n* 4. Zadajte skóre: ");
		String score = scanner.nextLine().trim();
		System.out.print("\n* 5. Zadajte mapu: ");
		String mapName = scanner.nextLine().trim();
		
		
		System.out.println("\nPotvrdiť uloženie? [y/n]: ");
		String choice = scanner.nextLine().trim();
        
        switch (choice) {
        case "y":
        	if (!score.matches("\\d{1,2}-\\d{1,2}")) {
        		validationErrorMessage(4);
        	} else {
            	MapDTO m = MapRepository.insert(mapName);
            	System.out.println(MapRepository.findAll());
            	saveSuccessMessage(m.getId());
        	}
        	break;
        case "n":
        	newMatchForm();
        	break;
        default:
        	invalidChoice();
        	break;
        }
        //return null;
	}
	
	private static void editExistingMatch() {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("               Editácia existujúceho zápasu");
		System.out.println("=====================================================\n");
		
		System.out.println("Aktuálne zápasy:");
		List<MapDTO> maps = MapRepository.findAll();
		
		for (MapDTO map : maps) {
			System.out.println("[ " + map.getId() + " ] - " + map.getName());
		}
		
	}
	
	
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
	
	
	
	//TODO dokoncit osetrovacky pre zvysne parametre
	//line predstavuje cislo riadku v ktorom bol chybny parameter
	private static void validationErrorMessage(int line) {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("                  CHYBA VALIDÁCIE");
		System.out.println("=====================================================\n");
		
		switch(line) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				System.out.println("Neplatný formát skóre. Skóre musí byť v tvare M-N (napr. 7-5 alebo 8-7).");
				System.out.println("Chyba nájdená v poli: Skóre.");
				break;
			case 5:
				break;
		}
		
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
	
	private static void loginScreen() {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("        R6 správca zápasov - Prihlásenie");
		System.out.println("=====================================================\n");
		
		System.out.print("\nMeno: ");
		caster = scanner.nextLine().trim();
		
		matches();
	}
	
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
        	wellcomeScreen(); //return to main menu
        case "n":
        	aboutScreen();
        	break;
        default:
        	invalidChoice();
        	break;
        }
	}
	
	private static void endScreen() {
		clearConsole();
		System.out.println("\n=====================================================");
		System.out.println("                       KONIEC");
		System.out.println("=====================================================\n");
		log.info("Closing Console application.");
		System.exit(0);
	}
}

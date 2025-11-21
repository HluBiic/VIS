package app.utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import dal.EntityKind;

/**
 * Singleton design pattern class responsible for logging a persistent file (app.log).
 */
public class Logger implements Listener {

    private static final String LOG_FILE = "app.log";
    private static final Logger instance = new Logger();
    /**
     * Provides the single, globally accessible instance of the Logger.
     */
    public static Logger getInstance() { 
    	return instance; 
    }

    /**
     * Private constructor for singleton.
     */
    private Logger() {}

    /**
     * Writes the given log text to the application log file.
     */
    private synchronized void writeToFile(String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
	 * Logs a CREATE, with timestamp, entity type, ID, and caster.
	 */
	@Override
	public void onCreated(int id, EntityKind entity, String caster) {
		LocalDateTime  today = LocalDateTime .now();
		writeToFile("CREATE [" + today + "] " + entity + " ID: " + id + " by CASTER: " + caster);
	}

	/**
	 * Logs a DELETE, with timestamp, entity type, ID, and caster.
	 */
	@Override
	public void onDeleted(int id, EntityKind entity, String caster) {
		LocalDateTime  today = LocalDateTime .now();
		writeToFile("DELETE [" + today + "] " + entity + " ID: " + id + " by CASTER: " + caster);
	}

	/**
	 * Logs a UPDATE, with timestamp, entity type, ID, and caster.
	 */
	@Override
	public void onUpdated(int id, EntityKind entity, String caster) {
		LocalDateTime  today = LocalDateTime .now();
		writeToFile("UPDATE [" + today + "] " + entity + " ID: " + id + " by CASTER: " + caster);
	}
}
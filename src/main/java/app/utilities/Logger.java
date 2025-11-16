package app.utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import dal.EntityKind;

public class Logger implements Listener {

    private static final String LOG_FILE = "app.log";

    private static final Logger instance = new Logger();
    public static Logger getInstance() { return instance; }

    private Logger() {}

    private synchronized void writeToFile(String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void onCreated(int id, EntityKind entity, String caster) {
		LocalDateTime  today = LocalDateTime .now();
		writeToFile("CREATE [" + today + "] " + entity + " ID: " + id + " by CASTER: " + caster);
	}
}

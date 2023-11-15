package log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.rmi.server.LogStream.log;

public class Log {

    private static final Integer MAX_FILE_SIZE = 1000000; // Limite de tamanho do arquivo em bytes;
    private static final String LOG_DIRECTORY = "logs/";
    private static final String LOG_FILE_PREFIX = "AlpacaLogs";
    private static final String LOG_FILE_EXTENSION = ".txt";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws IOException {
        generateLog("Mensagem de erro");
    }

    private static String getLogFileName() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File logsDir = new File(LOG_DIRECTORY);

        if (!logsDir.exists()) {
            logsDir.mkdirs();
            return LOG_DIRECTORY + LOG_FILE_PREFIX + LOG_FILE_EXTENSION;

        }
        return LOG_DIRECTORY + LOG_FILE_PREFIX + LOG_FILE_EXTENSION;
    }

    private static String getLogLine(String message) {
        String timestamp = dateFormat.format(new Date());
        return String.format("[%s] %s", timestamp, message);
    }


    public static void generateLog(String messagem) throws IOException {
        String logLine = getLogLine(messagem);
        String currentLogFileName = getLogFileName();
        File currentLogFile = new File(currentLogFileName);
        if (currentLogFile.exists() && currentLogFile.length() > MAX_FILE_SIZE) {
            currentLogFileName = getLogFileName();
            currentLogFile = new File(currentLogFileName);
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(currentLogFile, true))) {
            out.println(logLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

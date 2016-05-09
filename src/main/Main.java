package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import widget.window.MainWindow;

public class Main {

	static PrintWriter writer;
	static final String LOG_PATH = "log"; // use '/' as file separator

	private static void prepareLog() throws FileNotFoundException, UnsupportedEncodingException {
		String filename = new Date().getTime() + ".txt";
		String log_path = LOG_PATH + '/' + filename;
		log_path = log_path.replaceAll("/", File.separator);
		writer = new PrintWriter(log_path, "UTF-8");
	}

	public static void main(String[] args) {
		try {
			prepareLog();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println("Can not create log file.");
			return;
		}
		log("first log");
		MainWindow window = MainWindow.getInstance();
		window.pack();
		window.show();

		writer.close();
	}

	public static void log(String message) {
		System.out.println(message);
		writer.println(message);
	}

}

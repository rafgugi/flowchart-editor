package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import widget.window.MainWindow;

public class Main {

	static PrintWriter writer;

	private static void prepareLog() throws FileNotFoundException, UnsupportedEncodingException {
		String filename = new Date().getTime() + "";
		writer = new PrintWriter(filename, "UTF-8");
	}

	public static void main(String[] args) {
		try {
			prepareLog();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println("Can not create log file.");
			return;
		}

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

package main;

import widget.window.MainWindow;

public class Main {

	public static void main(String[] args) {
		MainWindow window = MainWindow.getInstance();
		window.pack();
		window.show();
	}

}

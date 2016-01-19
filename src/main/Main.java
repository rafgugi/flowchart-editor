package main;

import widget.Window;

public class Main {

	public static void main(String[] args) {
		Window window = Window.getInstance();
        window.pack();
		window.show();
	}

}

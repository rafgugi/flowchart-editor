package command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import diagram.Flowchart;
import exception.PersistenceException;
import interfaces.ICommand;
import interfaces.IElement;
import interfaces.ISubEditor;
import main.Main;
import widget.window.MainWindow;

public class OpenCommand implements ICommand {

	@Override
	public void execute() {
		FileDialog fd = new FileDialog(MainWindow.getInstance(), SWT.OPEN);
		fd.setText("Open");

		String[] filterExt = { "*.json", "*.*" };
		fd.setFilterExtensions(filterExt);
		String filename = fd.open();
		if (filename == null) {
			return;
		}
		MainWindow.getInstance().setStatus("Open file " + filename);
		String json = null;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				json = line;
			}
			reader.close();
		} catch (Exception e) {
			Main.log(e.getMessage());
		}
		if (json != null) {
			boolean success = false;
			try {
				JSONObject obj = new JSONObject(json);
				int index = filename.lastIndexOf(File.separator) + 1;

				beginDecoding(obj, filename.substring(index));
				success = true;
			} catch (JSONException e) {
				throw new PersistenceException("Wrong JSON file.");
			} finally {
				if (!success) {
					new CloseTabCommand().execute();
				}
			}
		}
	}

	public void beginDecoding(JSONObject obj, String filename) {
		JSONArray elements = obj.getJSONArray("elements");
		MainWindow.getInstance().getEditor().newSubEditor(filename);
		ISubEditor editor = MainWindow.getInstance().getEditor().getActiveSubEditor();
		editor.setDiagram(Flowchart.get());
		for (int i = 0; i < elements.length(); i++) {
			JSONObject item = elements.getJSONObject(i);
			String className = item.getString("class");
			IElement elem = null;
			try {
				elem = (IElement) Class.forName(className).getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException
					| ClassNotFoundException e) {
				throw new PersistenceException("Create element: " + e.getMessage());
			}
			if (elem == null) {
				throw new PersistenceException("Wrong JSON format: wrong class name.");
			}
			elem.jsonDecode(item);
			editor.addElement(elem);
		}
	}

}

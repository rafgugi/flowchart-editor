package command;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.json.JSONObject;

import diagram.element.AEditable;
import interfaces.ICommand;
import interfaces.IElement;
import widget.window.MainWindow;

public class SaveCommand implements ICommand {

	@Override
	public void execute() {
		List<IElement> elements = MainWindow.getInstance().getEditor().getActiveSubEditor().getElements();

		JSONObject retval = new JSONObject();
		for (IElement i : elements) {
			AEditable elem = (AEditable) i;
			JSONObject obj = elem.jsonEncode();
			retval.append("elements", obj);

			System.out.println(obj);
		}

        FileDialog fd = new FileDialog(MainWindow.getInstance(), SWT.SAVE);
        fd.setText("Save");

        String[] filterExt = {"*.json", "*.*"};
        fd.setFilterExtensions(filterExt);
        String selected = fd.open();
        if (selected != null) {
            PrintWriter writer;
    		try {
    			writer = new PrintWriter(selected, "UTF-8");
    	        writer.print(retval);
    	        writer.close();
    		} catch (FileNotFoundException | UnsupportedEncodingException e) {
    			e.printStackTrace();
    		}
        }
	}

	public void nyobaktok() {
		JSONObject obj = new JSONObject();
		String className = obj.getClass().getName();
		JSONObject o = null;
		try {
			o = (JSONObject) Class.forName(className).getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException 
				| InvocationTargetException | NoSuchMethodException 
				| SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		o.append("key1", "value1");
		System.out.println(o);
	}
}

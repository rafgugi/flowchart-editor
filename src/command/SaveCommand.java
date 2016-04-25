package command;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import diagram.element.AEditable;
import interfaces.ICommand;
import interfaces.IElement;
import widget.window.MainWindow;

public class SaveCommand implements ICommand {

	@Override
	public void execute() {
		List<IElement> elements = MainWindow.getInstance().getEditor().getActiveSubEditor().getElements();
		
		JSONArray arr = new JSONArray();
		for (IElement i : elements) {
			UUID id = UUID.randomUUID();
			System.out.println(id.getLeastSignificantBits() + "_" + id.getMostSignificantBits());
			System.out.println(id);
			UUID another = UUID.fromString(id.toString());
			System.out.println(another);

			AEditable elem = (AEditable) i;
			JSONObject obj = elem.jsonEncode();
			arr.put(obj);
			System.out.println(obj);
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

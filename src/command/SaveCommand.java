package command;

import java.lang.reflect.InvocationTargetException;

import org.json.JSONObject;

import interfaces.ICommand;

public class SaveCommand implements ICommand {

	@Override
	public void execute() {
		JSONObject obj = new JSONObject();
		String className = obj.getClass().getName();
		JSONObject o = null;
		try {
			o = (JSONObject) Class.forName(className).getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		o.append("key1", "value1");
		System.out.println(o);
	}
}

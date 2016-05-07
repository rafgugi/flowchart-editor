package command;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.json.JSONObject;

import diagram.element.AEditable;
import interfaces.ICommand;
import interfaces.IElement;
import interfaces.ISubEditor;
import widget.window.MainWindow;

public class SaveCommand implements ICommand {

	@Override
	public void execute() {
		FileDialog fd = new FileDialog(MainWindow.getInstance(), SWT.SAVE);
		fd.setText("Save");

		String[] filterExt = {"*.json", "*.*"};
		fd.setFilterExtensions(filterExt);
		String filename = fd.open();

		if (filename != null) {
			/* Begin encoding the elements */
			ISubEditor s = MainWindow.getInstance().getEditor().getActiveSubEditor();
			List<IElement> elements = s.getElements();

			JSONObject retval = new JSONObject();
			for (IElement i : elements) {
				AEditable elem = (AEditable) i;
				JSONObject obj = elem.jsonEncode();
				retval.append("elements", obj);

				System.out.println(obj);
			}

			MainWindow.getInstance().setStatus("Save file " + filename);
			PrintWriter writer;
			try {
				writer = new PrintWriter(filename, "UTF-8");
				writer.print(retval);
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

}

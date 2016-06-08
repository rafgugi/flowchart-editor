package command;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import command.codegenerator.CodeAlgorithmCommand;
import command.codegenerator.ConvertToPADCommand;
import diagram.pad.BlockContainer;
import exception.GenerateCodeException;
import interfaces.ICommand;
import main.Main;
import widget.window.MainWindow;

public class ShowPADCommand implements ICommand {

	@Override
	public void execute() {
		ValidateDiagramCommand validator = new ValidateDiagramCommand();
		validator.execute();
		if (validator.isError()) {
			return;
		}

		boolean status = false;
		String padtext = "";
		try {
			CodeAlgorithmCommand codeAlgorithm = new CodeAlgorithmCommand();
			codeAlgorithm.execute();

			ConvertToPADCommand toPAD = new ConvertToPADCommand(codeAlgorithm.getFirstCode());
			toPAD.execute();
			
			BlockContainer fatherBlock = toPAD.getFatherBlock();
			padtext = fatherBlock.toString();
			Main.log(padtext);

			/*
			How to draw PAD element

			BlockContainer
				define x point and top y point
				iterate through all element
					define y point for each element based on text length
					save highest x length from 
			*/

			status = true;
		} catch (GenerateCodeException ex) {
			Main.log("Error: ");
			Main.log(ex.getMessage());
			MainWindow.getInstance().setStatus(ex.getMessage());
		} finally {
			if (status) {
				FileDialog dialog = new FileDialog(MainWindow.getInstance(), SWT.SAVE);
				dialog.setFilterExtensions(new String[] { "*.txt", "*.*" });
				String title = MainWindow.getInstance().getEditor().getActiveSubEditor().getTitle();
				dialog.setFileName(title + ".txt");
				String filepath = dialog.open();
				if (filepath == null) {
					throw new GenerateCodeException("Save canceled.");
				}
				Main.log("Save to " + filepath);

				PrintWriter writer;
				try {
					writer = new PrintWriter(filepath, "UTF-8");
					writer.println(padtext);
					writer.close();
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

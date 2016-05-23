package command.codegenerator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import command.ValidateDiagramCommand;
import exception.GenerateCodeException;
import interfaces.ICommand;
import main.Main;
import widget.window.MainWindow;

/**
 * This command purpose is generating source code from flowchart. There are
 * some algorithms to process this command:
 *     1. Validate the flowchart
 *     2. Generate code for each flowchart node
 *     3. Convert coded flowchart to PAD
 *     4. Convert PAD to raw source code
 *     5. Convert raw source code to source code
 */
public class GenerateCodeCommand implements ICommand {

	@Override
	public void execute() {
		ValidateDiagramCommand validator = new ValidateDiagramCommand();
		validator.execute();
		if (validator.isError()) {
			return;
		}

		boolean status = false;
		String blockcode = "";
		try {
			CodeAlgorithmCommand codeAlgorithm = new CodeAlgorithmCommand();
			codeAlgorithm.execute();

			ConvertToPADCommand toPAD = new ConvertToPADCommand();
			toPAD.setFirstCode(codeAlgorithm.getFirstCode());
			toPAD.execute();
			
			ConvertToCodeCommand toCode = new ConvertToCodeCommand();
			toCode.setFatherBlock(toPAD.getFatherBlock());
			toCode.execute();

			blockcode = toCode.getGeneratedCode();
			status = true;
		} catch (GenerateCodeException ex) {
			Main.log("Error: ");
			Main.log(ex.getMessage());
			MainWindow.getInstance().setStatus(ex.getMessage());
		} finally {
			if (status) {
				MainWindow.getInstance().setStatus("Finish generating code.");

				FileDialog dialog = new FileDialog(MainWindow.getInstance(), SWT.SAVE);
				dialog.setFilterExtensions(new String[] { "*.c", "*.*" });
				dialog.setFileName("output.c");
				String filepath = dialog.open();
				if (filepath == null) {
					throw new GenerateCodeException("Save canceled.");
				}
				Main.log("Save to " + filepath);

				PrintWriter writer;
				try {
					writer = new PrintWriter(filepath, "UTF-8");
					writer.println(blockcode);
					writer.close();
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

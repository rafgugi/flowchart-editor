package command;

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
		try {
			CodeAlgorithmCommand codeAlgorithm = new CodeAlgorithmCommand();
			codeAlgorithm.execute();

			ConvertToPADCommand toPAD = new ConvertToPADCommand(codeAlgorithm.getFirstCode());
			toPAD.execute();
			
			BlockContainer fatherBlock = toPAD.getFatherBlock();
			fatherBlock.getParent(); // ?

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
			}
		}
	}

}

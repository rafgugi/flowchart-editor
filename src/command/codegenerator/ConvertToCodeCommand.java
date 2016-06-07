package command.codegenerator;

import diagram.pad.BlockContainer;
import interfaces.ICommand;

public class ConvertToCodeCommand implements ICommand {

	private String generated;
	private BlockContainer fatherBlock;

	@Override
	public void execute() {
		generated = "";

		/* Template for C */
		generated += "#include <stdio.h>\n\n";
		generated += "void main() {\n";
		generated += fatherBlock.generate();
		generated += "}";
	}

	public String getGeneratedCode() {
		return generated;
	}
	
	public void setFatherBlock(BlockContainer block) {
		fatherBlock = block;
	}
}

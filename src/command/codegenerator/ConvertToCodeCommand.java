package command.codegenerator;

import diagram.pad.BlockContainer;
import interfaces.ICommand;

public class ConvertToCodeCommand implements ICommand {

	private String generated;
	private BlockContainer fatherBlock;

	@Override
	public void execute() {
		generated = "";
		generated += "void main() {\n";
		generated += fatherBlock.generate();
		generated += "}\n";
	}

	public String getGeneratedCode() {
		return generated;
	}
	
	public void setFatherBlock(BlockContainer block) {
		fatherBlock = block;
	}
}

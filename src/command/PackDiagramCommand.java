package command;

import interfaces.ICommand;

public class PackDiagramCommand implements ICommand {

	@Override
	public void execute() {
		NodeCode code1 = new NodeCode();
		NodeCode code2 = new NodeCode(code1);
		NodeCode code3 = code2.createSibling();
		NodeCode code4 = code1.createSibling();
		System.out.println(code1);
		System.out.println(code2);
		System.out.println(code3);
		System.out.println(code4);
	}

}

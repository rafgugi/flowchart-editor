package command;

import diagram.flowchart.NodeCode;
import interfaces.ICommand;
import main.Main;

public class PackDiagramCommand implements ICommand {

	@Override
	public void execute() {
		NodeCode code1 = new NodeCode();
		NodeCode code2 = code1.createChild(1);
		NodeCode code3 = code2.createSibling();
		NodeCode code4 = code1.createSibling();
		NodeCode code5 = code1.createChild(2);
		NodeCode code6 = code2.createChild(1);

		System.out.print("code1 new NodeCode(): ");
		Main.log(code1 + "");
		System.out.print("code2 code1.createChild(): ");
		Main.log(code2 + "");
		System.out.print("code3 code2.createSibling(): ");
		Main.log(code3 + "");
		System.out.print("code4 code1.createSibling(): ");
		Main.log(code4 + "");
		System.out.print("code5 code1.createChild(): ");
		Main.log(code5 + "");
		System.out.print("code6 code2.createChild(): ");
		Main.log(code6 + "");
	}

}

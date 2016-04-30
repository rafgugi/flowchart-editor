package command;

import diagram.flowchart.NodeCode;
import interfaces.ICommand;

public class PackDiagramCommand implements ICommand {

	@Override
	public void execute() {
		NodeCode code1 = new NodeCode();
		NodeCode code2 = code1.createChild();
		NodeCode code3 = code2.createSibling();
		NodeCode code4 = code1.createSibling();
		NodeCode code5 = code1.createChild();
		NodeCode code6 = code2.createChild();

		System.out.print("code1 new NodeCode(): ");
		System.out.println(code1);
		System.out.print("code2 code1.createChild(): ");
		System.out.println(code2);
		System.out.print("code3 code2.createSibling(): ");
		System.out.println(code3);
		System.out.print("code4 code1.createSibling(): ");
		System.out.println(code4);
		System.out.print("code5 code1.createChild(): ");
		System.out.println(code5);
		System.out.print("code6 code2.createChild(): ");
		System.out.println(code6);
	}

}

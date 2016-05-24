package command.codegenerator;

import diagram.element.Line;
import diagram.flowchart.*;
import diagram.flowchart.type.*;
import diagram.pad.*;
import interfaces.FlowChartElement;
import interfaces.ICommand;
import main.Main;

public class ConvertToPADCommand implements ICommand {

	private NodeCode firstCode;
	private BlockContainer fatherBlock;

	public ConvertToPADCommand(NodeCode firstCode) {
		this.firstCode = firstCode;
		Main.log("Start check the code");
		Main.log(firstCode.printTrace());
		Main.log("Finish check the code");
	}

	@Override
	public void execute() {
		fatherBlock = new BlockContainer();
		convertToPAD(firstCode, fatherBlock);
	}

	/**
	 * Convert the coded flowchart into PAD.
	 * 
	 * @param currCode
	 * @param fatherBlock
	 */
	public void convertToPAD(NodeCode currCode, BlockContainer fatherBlock) {
		if (currCode == null) {
			return;
		}
		FlowChartElement currElem = currCode.getElement();
		if (currElem == null) {
			return;
		}
		Main.log("ConvertToPAD enter " + currCode + " " + currElem);
		if (currElem.getType() instanceof ProcessType) {
			Sequence element = new Sequence();
			element.setText(currElem.getText());
			fatherBlock.addElement(element);
		}
		if (currElem.getType() instanceof SelectionType) {
			Selection element = new Selection();
			element.setText(currElem.getText());

			/* Create new layer, for each selection children */
			Main.log("Traverse each child flow which is not convergence.");
			for (FlowLine flow : ((Judgment) currElem).getFlows()) {
				FlowChartElement nextFlow = (FlowChartElement) flow.getDstElement();
				if (nextFlow instanceof Convergence) {
					continue;
				}
				BlockContainer subBlock = new BlockContainer();
				if (flow.getText().equals(Line.YES)) {
					element.setYesChild(subBlock);
				} else if (flow.getText().equals(Line.NO)) {
					element.setNoChild(subBlock);
				}
				convertToPAD(nextFlow.getNodeCode(), subBlock);
			}
			fatherBlock.addElement(element);
			Main.log("Finish traverse each child flow.");
		}
		if (currElem.getType() instanceof LoopType) {
			Loop element;
			if (currElem.getType() instanceof WhileType) {
				element = new While();
			} else {
				element = new DoWhile();
			}
			for (FlowLine flow : ((Judgment) currElem).getFlows()) {
				FlowChartElement nextFlow = (FlowChartElement) flow.getDstElement();
				if (nextFlow instanceof Convergence) {
					element.setFlowType(flow.getText().equals(Line.NO));
				}
			}
			element.setText(currElem.getText());
			BlockContainer subContainer = new BlockContainer();
			NodeCode childCode = currCode.getChildren().get(0);
			element.setChild(subContainer);
			fatherBlock.addElement(element);
			convertToPAD(childCode, subContainer);
		}
		/* Go to next code from the same layer */
		currCode = currCode.getSibling();
		convertToPAD(currCode, fatherBlock);
	}
	
	public BlockContainer getFatherBlock() {
		return fatherBlock;
	}

}

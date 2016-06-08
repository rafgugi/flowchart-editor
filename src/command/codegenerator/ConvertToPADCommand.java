package command.codegenerator;

import diagram.flowchart.*;
import diagram.flowchart.type.*;
import diagram.pad.*;
import exception.GenerateCodeException;
import interfaces.FlowChartElement;
import interfaces.ICommand;
import main.Main;

public class ConvertToPADCommand implements ICommand {

	private NodeCode firstCode;
	private BlockContainer fatherBlock;

	public ConvertToPADCommand(NodeCode firstCode) {
		this.firstCode = firstCode;
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
			for (NodeCode child : currCode.getChildren()) {
				FlowChartElement nextFlow = child.getElement();
				if (nextFlow instanceof Convergence) {
					continue;
				}
				BlockContainer subBlock = new BlockContainer(element);
				if (child.getX() == 1) {
					element.setYesChild(subBlock);
				} else if (child.getX() == 2) {
					element.setNoChild(subBlock);
				}
				if (nextFlow == null) {
					throw new GenerateCodeException("nexFlow null");
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
			element.setText(currElem.getText());
			BlockContainer subContainer = new BlockContainer(element);

			/* Loop element always have only 1 children */
			NodeCode childCode = currCode.getChildren().get(0);
			/* Set flowType from code's x value */
			element.setFlowType(childCode.getX() == 1);
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

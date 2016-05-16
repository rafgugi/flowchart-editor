package command;

import diagram.element.Line;
import diagram.flowchart.Convergence;
import diagram.flowchart.FlowLine;
import diagram.flowchart.Judgment;
import diagram.flowchart.NodeCode;
import diagram.flowchart.type.LoopType;
import diagram.flowchart.type.ProcessType;
import diagram.flowchart.type.SelectionType;
import diagram.flowchart.type.WhileType;
import diagram.pad.BlockContainer;
import diagram.pad.DoWhile;
import diagram.pad.Loop;
import diagram.pad.Selection;
import diagram.pad.Sequence;
import diagram.pad.While;
import exception.GenerateCodeException;
import interfaces.FlowChartElement;
import interfaces.ICommand;
import main.Main;

public class ConvertToPADCommand implements ICommand {

	private NodeCode firstCode;
	private BlockContainer fatherBlock;

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
		Main.log("ConvertToPAD enter " + currElem);
		if (currElem.getType() instanceof ProcessType) {
			Sequence element = new Sequence();
			element.setText(currElem.getText());
			fatherBlock.addElement(element);
		}
		if (currElem.getType() instanceof SelectionType) {
			Selection element = new Selection();
			element.setText(currElem.getText());
			
			/* Create new layer, for each selection children */
			Main.log("Go to each child flow which is not convergence.");
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
				} else {
					throw new GenerateCodeException("Judgment flow type isn't defined.");
				}
				convertToPAD(nextFlow.getNodeCode(), subBlock);
			}
			fatherBlock.addElement(element);
		}
		if (currElem.getType() instanceof LoopType) {
			Loop element;
			if (currElem.getType() instanceof WhileType) {
				element = new While();
			} else {
				element = new DoWhile();
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
	
	public void setFirstCode(NodeCode code) {
		firstCode = code;
	}
	
	public BlockContainer getFatherBlock() {
		return fatherBlock;
	}

}

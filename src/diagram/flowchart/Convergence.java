package diagram.flowchart;

import diagram.element.Line;
import diagram.element.TwoDimensional;
import diagram.flowchart.type.ConvergenceType;
import exception.CreateElementException;
import interfaces.IDiagramElement;
import interfaces.IElement;
import main.Main;
import widget.window.property.JudgmentProperty;

public class Convergence extends FlowChartDecorator implements IDiagramElement {

	private FlowLine flow;
	private Judgment directJudgment;

	public Convergence() {
	}

	public Convergence(TwoDimensional component) {
		super(component);
	}

	@Override
	public void select() {
		super.select();
		Main.log("Select " + this);
		Main.log("Type: " + getType());
		Main.log("Node Code:" + getNodeCode());
		Main.log("Convergence:" + getDirectJudgment());
	}

	@Override
	public void action() {
		JudgmentProperty prop = new JudgmentProperty(this);
		prop.show();
	}

	@Override
	public void connect(IElement element) {
		super.connect(element);
		if (element instanceof FlowLine) {
			FlowLine flow = (FlowLine) element;
			if (flow.checkConnected(this) == Line.CONNECTED_SRC) {
				if (this.flow != null) {
					throw new CreateElementException("Convergence can't have more than one children.");
				} else {
					this.flow = flow;
				}
			}
		}
	}

	@Override
	public void disconnect(IElement element) {
		if (element == flow) {
			flow = null;
		}
		super.disconnect(element);
	}

	/**
	 * Get flow that go from this element.
	 * 
	 * @return flow
	 */
	public FlowLine getFlow() {
		return flow;
	}

	/**
	 * Get the judgment pair of this.
	 * 
	 * @return judgment
	 */
	public Judgment getDirectJudgment() {
		return directJudgment;
	}

	/**
	 * Set the judgment pair.
	 * 
	 * @param judgment
	 */
	public void setDirectJudgment(Judgment directJudgment) {
		this.directJudgment = directJudgment;
	}

	@Override
	public void setNodeCode(NodeCode code) {
		this.nodeCode = code;
		Main.log("\tSet code of " + this + ": " + code);
	}

	@Override
	public void prepare() {
		super.prepare();
		setDirectJudgment(null);
		setType(ConvergenceType.get());
	}

}

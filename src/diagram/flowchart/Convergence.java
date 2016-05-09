package diagram.flowchart;

import diagram.element.Line;
import diagram.element.TwoDimensional;
import diagram.flowchart.type.ProcessType;
import exception.CreateElementException;
import interfaces.IDiagramElement;
import interfaces.IElement;
import widget.window.property.ProcessProperty;

public class Convergence extends FlowChartDecorator implements IDiagramElement {

	private FlowLine flow;

	private Decision directJudgment;

	public static final int FIX_DIAMETER = 15;

	public Convergence() {
	}

	public Convergence(TwoDimensional component) {
		super(component);
	}

//	@Override
//	public void setWidth(int h) {
//		super.setWidth(FIX_DIAMETER);
//	}
//
//	@Override
//	public void setHeight(int h) {
//		super.setHeight(FIX_DIAMETER);
//	}

	@Override
	public void select() {
		super.select();
		System.out.println("Select " + this);
		System.out.println("Type: " + getType());
		System.out.println("Node Code:" + getNodeCode());
	}

	@Override
	public void action() {
		ProcessProperty prop = new ProcessProperty(this);
		prop.show();
	}

	@Override
	public void connect(IElement element) {
		if (this.flow != null) {
			throw new CreateElementException("Convergence can't have more than one children.");
		}
		super.connect(element);
		if (element instanceof FlowLine) {
			FlowLine flow = (FlowLine) element;
			if (flow.checkConnected(this) == Line.CONNECTED_SRC) {
				this.flow = flow;
			}
		}
	}

	@Override
	public void disconnect(IElement element) {
		super.disconnect(element);
		flow = null;
	}

	public FlowLine getFlow() {
		return flow;
	}

	/**
	 * Get the judgment pair of this.
	 * 
	 * @return judgment
	 */
	public Decision getDirectJudgment() {
		return directJudgment;
	}

	/**
	 * Set the judgment pair.
	 * 
	 * @param judgment
	 */
	public void setDirectJudgment(Decision directJudgment) {
		this.directJudgment = directJudgment;
	}

	@Override
	public void prepare() {
		super.prepare();
		setType(ProcessType.get());
	}

	@Override
	public String getText() {
		String ans = super.getText();
		if (getNodeCode() != null) {
			ans += " " + getNodeCode();
		}
		return ans;
	}

}

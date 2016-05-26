package diagram.flowchart;

import java.util.ArrayList;

import diagram.element.Line;
import diagram.element.TwoDimensional;
import interfaces.FlowChartElement;
import interfaces.IDiagramElement;
import interfaces.IElement;
import main.Main;
import widget.window.property.JudgmentProperty;

public class Judgment extends FlowChartDecorator implements IDiagramElement {

	private ArrayList<FlowLine> flows = new ArrayList<>();
	private Convergence directConvergence;

	public Judgment() {
	}

	public Judgment(TwoDimensional component) {
		super(component);
	}

	@Override
	public void select() {
		super.select();
		Main.log("Select " + this);
		Main.log("Type: " + getType());
		Main.log("Node Code:" + getNodeCode());
		Main.log("DoWhile:" + getDoWhileNode());
		Main.log("Traversed:" + (isTraversed() ? "true" : "false"));
		Main.log("Convergence:" + getDirectConvergence());
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
				flows.add(flow);
			}
		}
	}

	@Override
	public void disconnect(IElement element) {
		if (flows.contains(element)) {
			flows.remove(element);
		}
		super.disconnect(element);
	}

	/**
	 * Get flows that go from this element.
	 * 
	 * @return flows
	 */
	public ArrayList<FlowLine> getFlows() {
		return flows;
	}

	/**
	 * Get flow that connect to element.
	 * 
	 * @param element
	 * @return flow
	 */
	public FlowLine getFlow(FlowChartElement element) {
		for (FlowLine flow : getFlows()) {
			if (flow.getSon() == element) {
				return flow;
			}
		}
		return null;
	}

	/**
	 * Get convergence pair of this.
	 * 
	 * @return directConvergence
	 */
	public Convergence getDirectConvergence() {
		return directConvergence;
	}

	/**
	 * Set convergence pair
	 * 
	 * @param directConvergence
	 */
	public void setDirectConvergence(Convergence directConvergence) {
		this.directConvergence = directConvergence;
	}

	@Override
	public void prepare() {
		super.prepare();
		setDirectConvergence(null);
	}

}

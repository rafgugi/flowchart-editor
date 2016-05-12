package diagram.flowchart;

import java.util.ArrayList;

import diagram.element.Line;
import diagram.element.TwoDimensional;
import interfaces.FlowChartElement;
import interfaces.IDiagramElement;
import interfaces.IElement;
import main.Main;
import widget.window.property.ProcessProperty;

public class Judgment extends FlowChartDecorator implements IDiagramElement {

	private ArrayList<FlowLine> flows = new ArrayList<>();
	private Convergence directConvergence;
	private FlowChartElement doWhileNode;

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
		Main.log("DoWhile:" + getDoWhileCounter());
		Main.log("RecodeDoWhile:" + getRecodeDoWhileCounter());
		Main.log("Traversed:" + (hasBeenTraversed() ? "true" : "false"));
		Main.log("Convergence:" + getDirectConvergence());
	}

	@Override
	public void action() {
		ProcessProperty prop = new ProcessProperty(this);
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
		super.disconnect(element);
		flows.remove(element);
	}

	public ArrayList<FlowLine> getFlows() {
		return flows;
	}

	public Convergence getDirectConvergence() {
		return directConvergence;
	}

	public void setDirectConvergence(Convergence directConvergence) {
		this.directConvergence = directConvergence;
	}

	/**
	 * Node where do while child begin.
	 */
	public FlowChartElement getDoWhileNode() {
		return doWhileNode;
	}

	public void setDoWhileNode(FlowChartElement node) {
		doWhileNode = node;
	}

	@Override
	public void prepare() {
		super.prepare();
		setDirectConvergence(null);
		setDoWhileNode(null);
	}

}
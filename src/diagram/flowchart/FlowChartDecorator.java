package diagram.flowchart;

import diagram.element.TwoDimensional;
import diagram.element.TwoDimensionalDecorator;
import diagram.flowchart.type.ProcessType;
import interfaces.FlowChartElement;
import interfaces.IDiagramElement;
import interfaces.IType;

public abstract class FlowChartDecorator extends TwoDimensionalDecorator implements FlowChartElement, IDiagramElement {

	private NodeCode nodeCode;
	private IType type;
	private boolean traversed;
	private int doWhile;
	private int recodeDoWhile;
	private Decision doWhileNode;
	
	public FlowChartDecorator() {
	}

	public FlowChartDecorator(TwoDimensional element) {
		super(element);
	}

	@Override
	public NodeCode getNodeCode() {
		return nodeCode;
	}

	@Override
	public void setNodeCode(NodeCode code) {
		this.nodeCode = code;
	}

	@Override
	public IType getType() {
		return type;
	}

	@Override
	public void setType(IType type) {
		this.type = type;
	}

	@Override
	public boolean hasBeenTraversed() {
		return traversed;
	}

	@Override
	public void resetTraversed() {
		traversed = false;
	}

	@Override
	public void traverse() {
		traversed = true;
	}

	@Override
	public void prepare() {
		resetTraversed();
		setType(null);
		setNodeCode(null);
		setDoWhileCounter(0);
		setRecodeDoWhileCounter(0);
		setDoWhileNode(null);
		setType(ProcessType.get());
	}

	@Override
	public int getDoWhileCounter() {
		return doWhile;
	}

	@Override
	public void setDoWhileCounter(int counter) {
		doWhile = counter;
	}

	@Override
	public int getRecodeDoWhileCounter() {
		return recodeDoWhile;
	}

	@Override
	public void setRecodeDoWhileCounter(int counter) {
		recodeDoWhile = counter;
	}

	@Override
	public FlowChartElement getDoWhileNode() {
		return doWhileNode;
	}

	@Override
	public void setDoWhileNode(FlowChartElement node) {
		doWhileNode = (Decision) node;
	}

	@Override
	public void select() {
		super.select();
		System.out.println("Select " + this);
		System.out.println("Type: " + getType());
		System.out.println("Node Code:" + getNodeCode());
	}

}

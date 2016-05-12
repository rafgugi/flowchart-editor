package diagram.flowchart;

import diagram.element.TwoDimensional;
import diagram.element.TwoDimensionalDecorator;
import interfaces.FlowChartElement;
import interfaces.IType;
import main.Main;

public abstract class FlowChartDecorator extends TwoDimensionalDecorator implements FlowChartElement {

	protected NodeCode nodeCode;
	private IType type;
	private boolean traversed;
	private int doWhile;
	private int recodeDoWhile;
	
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
		if (code != null) {
			code.setElement(this);
		}
		Main.log("\tSet code of " + this + ": " + code);
	}

	@Override
	public IType getType() {
		return type;
	}

	@Override
	public void setType(IType type) {
		this.type = type;
		Main.log("\tSet type of " + this + ": " + type);
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
	public void select() {
		super.select();
		Main.log("Select " + this);
		Main.log("Type: " + getType());
		Main.log("Node Code:" + getNodeCode());
	}

}

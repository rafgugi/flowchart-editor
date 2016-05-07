package diagram.flowchart;

import org.eclipse.swt.graphics.Point;

import diagram.element.Line;
import diagram.element.RoundedRectangle;
import diagram.flowchart.type.TerminatorType;
import exception.CreateElementException;
import interfaces.FlowChartElement;
import interfaces.IDiagramElement;
import interfaces.IElement;
import interfaces.IType;
import widget.window.property.TerminatorProperty;

public class Terminator extends RoundedRectangle implements IDiagramElement, FlowChartElement {

	private FlowLine flow;
	private NodeCode nodeCode;
	private IType type;
	private boolean traversed;
	private int doWhile;
	private int recodeDoWhile;
	private Decision doWhileNode;

	public static String START = "Start";
	public static String END = "End";

	public Terminator() {
	}

	public Terminator(Point src, Point dst) {
		super(src, dst);
		setText(START);
		flow = null;
	}

	@Override
	public void select() {
		super.select();
		System.out.println("Select " + this);
		System.out.println("Type: " + getType());
		System.out.println("Node Code:" + getNodeCode());
	}

	@Override
	public void action() {
		new TerminatorProperty(this).show();
	}

	@Override
	public void connect(IElement element) {
		super.connect(element);
		if (element instanceof FlowLine) {
			FlowLine flow = (FlowLine) element;
			if (flow.checkConnected(this) == Line.CONNECTED_SRC) {
				if (this.flow != null) {
					throw new CreateElementException("Terminator can't have more than one children.");
				} else {
					this.flow = flow;
				}
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
	public void prepare() {
		setType(null);
		setNodeCode(null);
		setDoWhileCounter(0);
		setRecodeDoWhileCounter(0);
		setType(TerminatorType.get());
	}

}

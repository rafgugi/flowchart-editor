package diagram.flowchart;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import diagram.element.Diamond;
import diagram.element.Line;
import interfaces.FlowChartElement;
import interfaces.IDiagramElement;
import interfaces.IElement;
import interfaces.IType;
import main.Main;
import widget.window.property.ProcessProperty;

public class Decision extends Diamond implements IDiagramElement, FlowChartElement {

	private ArrayList<FlowLine> flows = new ArrayList<>();
	private NodeCode nodeCode;
	private IType type;
	private boolean tranversed;
	private int doWhile;
	private int recodeDoWhile;
	private FlowChartElement doWhileNode;

	private Convergence directConvergence;

	public Decision() {
	}

	public Decision(Point src, Point dst) {
		super(src, dst);
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
		return tranversed;
	}

	@Override
	public void resetTraversed() {
		tranversed = false;
	}

	@Override
	public void traverse() {
		tranversed = true;
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
		doWhileNode = node;
	}

	public Convergence getDirectConvergence() {
		return directConvergence;
	}

	public void setDirectConvergence(Convergence directConvergence) {
		this.directConvergence = directConvergence;
	}

	@Override
	public void prepare() {
		setType(null);
		setNodeCode(null);
		setDoWhileCounter(0);
		setRecodeDoWhileCounter(0);
		setDirectConvergence(null);
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

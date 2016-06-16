package widget.toolbar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ToolBar;

import interfaces.ITool;
import interfaces.IToolStrip;
import widget.toolbar.tools.*;
import widget.window.MainWindow;

public class ToolStrip extends ToolBar implements IToolStrip {

	private List<ITool> tools = new ArrayList<>();

	public ToolStrip(MainWindow parent) {
		super(parent, SWT.BORDER | SWT.VERTICAL);
		initialize();
	}

	protected void initialize() {
		GridData gridData = new GridData();
		gridData.verticalAlignment = SWT.FILL;
		super.setLayoutData(gridData);

		addTool(new PointerTool(this));
		addTool(new TerminatorTool(this));
		addTool(new ProcessTool(this));
		addTool(new JudgmentTool(this));
		addTool(new ConvergenceTool(this));
		// addTool(new LineTool(this));
		addTool(new PolylineTool(this));

		tools.get(0);

		super.pack();
	}

	@Override
	public List<ITool> getTools() {
		return tools;
	}

	@Override
	public void addTool(ITool tool) {
		tools.add(tool);
	}

	@Override
	public void resetTools() {
		Iterator<ITool> iterator = tools.iterator();
		while (iterator.hasNext()) {
			ITool tool = (ITool) iterator.next();
			tools.remove(tool);
		}
	}

	@Override
	public void checkSubclass() {
	}

}

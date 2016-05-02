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

	public ToolStrip(MainWindow parent, int style) {
		super(parent, style);
		initialize();
	}

	public ToolStrip(MainWindow parent) {
		this(parent, SWT.BORDER | SWT.VERTICAL);
	}

	@Override
	public void initialize() {
		GridData gridData = new GridData();
		gridData.verticalAlignment = SWT.FILL;
		super.setLayoutData(gridData);
		
		addTool(new PointerTool(this));
		addTool(new EllipseTool(this));
		addTool(new RectangleTool(this));
		addTool(new DiamondTool(this));
		addTool(new LineTool(this));
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

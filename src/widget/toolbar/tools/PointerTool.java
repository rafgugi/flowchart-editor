package widget.toolbar.tools;

import java.util.ArrayList;

import org.eclipse.swt.events.MouseEvent;

import command.ElementPropertiesCommand;
import interfaces.IEditableElement;
import interfaces.IElement;

import widget.toolbar.ToolStrip;

public class PointerTool extends ATool {

	private MouseEvent downTemp;
	private IElement clickedElement;

	private static final int DRAG_SINGLE = 1;
	private static final int DRAG_MULTIPLE = 2;
	private static final int DRAG_CONTROL_POINT = 3;
	private static final int SELECT_MULTIPLE = 4;

	private int state;

	public PointerTool(ToolStrip parent, String name) {
		super(parent, name);
	}

	public PointerTool(ToolStrip parent) {
		super(parent, "Pointer tool");
	}

	protected void initialize() {
		setIconName("pointerx.png");
		super.initialize();
	}

	@Override
	public void mouseDown(MouseEvent e) {
		isDrag = false;
		downTemp = e;
		clickedElement = null;
		state = 0;

		IElement dapetElement = getActiveSubEditor().getElement(e.x, e.y);
		if (getActiveSubEditor().getSelectedElements().isEmpty()) {
			if (dapetElement != null) {
				dapetElement.select();
				clickedElement = dapetElement;
				state = DRAG_SINGLE;
			} else {
				state = SELECT_MULTIPLE;
			}
		} else {
			if (dapetElement != null) {
				clickedElement = dapetElement;
				if (dapetElement.isActive()) {
					if (dapetElement instanceof IEditableElement) {
						state = DRAG_MULTIPLE;
					} else {
						state = DRAG_CONTROL_POINT;
					}
				} else {
					state = DRAG_SINGLE;
				}
			} else {
				getActiveSubEditor().deselectAll();
				state = SELECT_MULTIPLE;
			}
		}
	}

	@Override
	public void mouseUp(MouseEvent e) {
		if (isDrag) {
			switch (state) {
			case DRAG_MULTIPLE:
				ArrayList<IElement> temp = new ArrayList<>();
				for (IElement element : getActiveSubEditor().getSelectedElements()) {
					element.drag(e.x - downTemp.x, e.y - downTemp.y);
					temp.add(element);
				}
				getActiveSubEditor().deselectAll();
				for (IElement element : temp) {
					element.select();
				}
				break;
			case DRAG_SINGLE:
			case DRAG_CONTROL_POINT:
				clickedElement.drag(e.x - downTemp.x, e.y - downTemp.y);
				getActiveSubEditor().deselectAll();
				clickedElement.select();
				break;
			case SELECT_MULTIPLE:
				for (IElement element : getActiveSubEditor().getElements()) {
					if (element.checkBoundary(downTemp.x, downTemp.y, e.x, e.y) != null) {
						element.select();
					}
				}
			}
		} else {
			getActiveSubEditor().deselectAll();
			if (clickedElement != null) {
				clickedElement.select();
			}
		}
		getActiveSubEditor().draw();
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		new ElementPropertiesCommand().execute();
	}

	@Override
	public void mouseMove(MouseEvent e) {
	}

}

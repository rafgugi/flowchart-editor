package widget.toolbar.tools;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseEvent;

import command.ElementPropertiesCommand;
import interfaces.IElement;

import java.util.ArrayList;
import widget.toolbar.ToolStrip;

public class PointerTool extends ATool {

	private boolean isDrag;
	private MouseEvent downTemp;
	private IElement clickedElement;

	public PointerTool(ToolStrip parent, String name) {
		super(parent, name);
	}

	public PointerTool(ToolStrip parent) {
		super(parent, "Pointer tool");
	}

	@Override
	public void initialize() {
		setIconName("pointerx.png");
		super.initialize();
	}

	@Override
	public void mouseDown(MouseEvent e) {
		isDrag = false;
		downTemp = e;
		clickedElement = null;

		IElement element = getActiveSubEditor().getElement(e.x, e.y);
		if (element != null) {
			/* select an element which previously no selected elements */
			clickedElement = element;
			getActiveSubEditor().draw();
		} else {
			/* select some elements */
		}

	}

	@Override
	public void mouseUp(MouseEvent e) {
		/*
		 * Jika nggak didrag maka select 1 elemen
		 */
		if (!isDrag) {
			/* Select an element */
			getActiveSubEditor().deselectAll();
			if (clickedElement != null) {
				clickedElement.select();
			}
		}
		/*
		 * Jika didrag maka pilihannya select banyak elemen ngedrag elemen. Nah
		 * ini elemennya dibatasi satu saja.
		 */
		else {
			/* Select some elements */
			if (clickedElement == null) {
				ArrayList<IElement> temp = new ArrayList<>();
				for (IElement element : getActiveSubEditor().getElements()) {
					if (element.checkBoundary(e.x, e.y, downTemp.x, downTemp.y) != null) {
						temp.add(element);
					}
				}
				
				for (IElement element : temp) {
					element.select();
				}
			} else {
				/*
				 * Move selected elements. Could this be more general? You can't
				 * move a line
				 */
				clickedElement.drag(downTemp.x, downTemp.y, e.x, e.y);
				getActiveSubEditor().deselectAll();
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
	public void dragDetected(DragDetectEvent e) {
		isDrag = true;
	}

	@Override
	public void mouseMove(MouseEvent e) {
		if (isDrag) {
			if (getActiveSubEditor().getSelectedElements().isEmpty()) {
				// select some items
			} else {
				// move selected items
			}
		}
	}

}

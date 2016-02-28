package widget.toolbar.tools;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseEvent;

import command.ElementPropertiesCommand;
import interfaces.IElement;
import java.util.List;
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
		List<IElement> elements = getActiveSubEditor().getSelectedElements();

		/*
		 * Kalau ga ada element yang active sekarang, maka pilihannya dia mau
		 * nyelect banyak atau ngedrag 1 elemen aja.
		 */
		if (elements.isEmpty()) {
			IElement element = getActiveSubEditor().getElement(e.x, e.y);
			if (element != null) {
				/* select an element which previously no selected elements */
				element.select();
				clickedElement = element;
				getActiveSubEditor().draw();
			} else {
				/* select some elements */
			}
		}
		/*
		 * Kalau udah ada elemen yg aktif, maka pilihannya dia mau ngedrag semua
		 * atau membatalkan seleksi.
		 */
		else {
			boolean isBoundary = false;
			for (IElement element : elements) {
				if (element.checkBoundary(e.x, e.y)) {
					isBoundary = true;
					clickedElement = element;
				}
			}
			if (isBoundary) {
				// move all selected elements
			} else {
				// reset selection
				getActiveSubEditor().deselectAll();
				mouseDown(e);
			}
		}

	}

	@Override
	public void mouseUp(MouseEvent e) {
		/*
		 * Jika nggak didrag maka pilihannya cuma select 1 elemen atau tidak
		 * sama sekali.
		 */
		if (!isDrag) {
			/* Select an element */
			getActiveSubEditor().deselectAll();
			IElement element = getActiveSubEditor().getElement(e.x, e.y);
			if (element != null) {
				element.select();
			}
		}
		/*
		 * Jika didrag maka pilihannya select banyak elemen ngedrag elemen. Nah
		 * ini elemennya dibatasi satu saja.
		 */
		else {
			/* Select some elements */
			if (clickedElement == null) {
				for (IElement element : getActiveSubEditor().getElements()) {
					if (element.checkBoundary(e.x, e.y, downTemp.x, downTemp.y)) {
						element.select();
					}
				}
			} else {
				/*
				 * Move selected elements. Could this be more general? You can't
				 * move a line
				 */
				clickedElement.drag(downTemp.x, downTemp.y, e.x, e.y);
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

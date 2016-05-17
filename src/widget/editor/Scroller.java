package widget.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

public class Scroller implements Listener {

	private Canvas canvas;
	private ScrollBar scroll;
	private int translate = 0;

	Scroller(Canvas canvas, ScrollBar scroll) {
		this.canvas = canvas;
		this.scroll = scroll;
		scroll.addListener(SWT.Selection, this);
	}

	@Override
	public void handleEvent(Event arg0) {
		int selection = scroll.getSelection();
		int dest = -selection - translate;
		Point size = canvas.getSize();
		canvas.scroll(dest, 0, 0, 0, size.x, size.y, false);
		translate = -selection;
	}

	public int getTranslate() {
		return translate;
	}

}

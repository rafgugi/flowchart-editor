package diagram.element;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class FlowLine extends AElement {

	private TwoDimensional srcElement;
	private TwoDimensional dstElement;
	
	public FlowLine(TwoDimensional src, TwoDimensional dst) {
		srcElement = src;
		dstElement = dst;
	}

	@Override
	public void renderNormal() {
		GC gc = new GC(getCanvas());
		Color black = new Color(gc.getDevice(), 0, 0, 0);
		Color white = new Color(gc.getDevice(), 255, 255, 255);
		gc.setForeground(black);
		gc.setBackground(white);

		int srcx = srcElement.getX() + srcElement.getWidth() / 2;
		int srcy = srcElement.getY() + srcElement.getHeight() / 2;
		int dstx = dstElement.getX() + dstElement.getWidth() / 2;
		int dsty = dstElement.getY() + dstElement.getHeight() / 2;
		
		gc.drawLine(srcx, srcy, dstx, dsty);
		
		gc.dispose();
	}

	@Override
	public void renderEdit() {
		renderNormal();
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkBoundary(int x, int y) {
		return false;
	}
	
	@Override
	public boolean checkBoundary(int x1, int y1, int x2, int y2) {
		return false;
	}
	
	public TwoDimensional getSrcElement() {
		return srcElement;
	}
	
	public void setSrcElement(TwoDimensional element) {
		srcElement = element;
	}
	
	public TwoDimensional getDstElement() {
		return dstElement;
	}
	
	public void setDstElement(TwoDimensional element) {
		dstElement = element;
	}

}

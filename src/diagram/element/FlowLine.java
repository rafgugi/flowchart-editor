package diagram.element;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

public class FlowLine extends AElement {

	private TwoDimensional srcElement;
	private TwoDimensional dstElement;

	public static int NOT_CONNECTED = 0;
	public static int CONNECTED_SRC = 1;
	public static int CONNECTED_DST = 2;

	public FlowLine(TwoDimensional src, TwoDimensional dst) {
		srcElement = src;
		dstElement = dst;
	}
	
	public static void draw(GC gc, int srcx, int srcy, int dstx, int dsty) {
		gc.drawLine(srcx, srcy, dstx, dsty);

		int n = 10;
		double angle = Math.atan2(dsty - srcy, dstx - srcx);
		int x1 = (int) (dstx - n * Math.cos(angle - Math.PI / 6));
		int y1 = (int) (dsty - n * Math.sin(angle - Math.PI / 6));
		int x2 = (int) (dstx - n * Math.cos(angle + Math.PI / 6));
		int y2 = (int) (dsty - n * Math.sin(angle + Math.PI / 6));

		int[] points = { dstx, dsty, x1, y1, x2, y2 };
		gc.fillPolygon(points);
		gc.drawPolygon(points);
	}

	@Override
	public void renderNormal() {
		GC gc = new GC(getCanvas());
		Color black = new Color(gc.getDevice(), 0, 0, 0);
		gc.setForeground(black);
		gc.setBackground(black);

		ArrayList<Point> srcPoints;
		srcPoints = new ArrayList<>();
		srcPoints.add(new Point(srcElement.getX() + srcElement.getWidth() / 2, srcElement.getY()));
		srcPoints.add(new Point(srcElement.getX(), srcElement.getY() + srcElement.getHeight() / 2));
		srcPoints.add(new Point(srcElement.getX() + srcElement.getWidth() / 2, srcElement.getY() + srcElement.getHeight()));
		srcPoints.add(new Point(srcElement.getX() + srcElement.getWidth(), srcElement.getY() + srcElement.getHeight() / 2));

		ArrayList<Point> dstPoints;
		dstPoints = new ArrayList<>();
		dstPoints.add(new Point(dstElement.getX() + dstElement.getWidth() / 2, dstElement.getY()));
		dstPoints.add(new Point(dstElement.getX(), dstElement.getY() + dstElement.getHeight() / 2));
		dstPoints.add(new Point(dstElement.getX() + dstElement.getWidth() / 2, dstElement.getY() + dstElement.getHeight()));
		dstPoints.add(new Point(dstElement.getX() + dstElement.getWidth(), dstElement.getY() + dstElement.getHeight() / 2));

		double min = Double.MAX_VALUE;
		int srcx = 0;
		int srcy = 0;
		int dstx = 0;
		int dsty = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				double euc = 0;
				euc += Math.pow(srcPoints.get(i).x - dstPoints.get(j).x, 2);
				euc += Math.pow(srcPoints.get(i).y - dstPoints.get(j).y, 2);
				euc = Math.sqrt(euc);
				if (euc < min) {
					srcx = srcPoints.get(i).x;
					srcy = srcPoints.get(i).y;
					dstx = dstPoints.get(j).x;
					dsty = dstPoints.get(j).y;
					min = euc;
				}
			}
		}
		
		draw(gc, srcx, srcy, dstx, dsty);
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

	@Override
	public void drag(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
	}

	public int checkConnected(TwoDimensional element) {
		if (srcElement == element) {
			return CONNECTED_SRC;
		}
		if (dstElement == element) {
			return CONNECTED_DST;
		}
		return NOT_CONNECTED;
	}

}

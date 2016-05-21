package diagram.element;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;
import org.json.JSONArray;
import org.json.JSONObject;

import interfaces.IElement;

public class PolyLine extends Line {

	private ArrayList<Point> elbows = new ArrayList<>();

	public PolyLine() {
	}

	public PolyLine(TwoDimensional src, TwoDimensional dst) {
		setSrcElement(src);
		setDstElement(dst);
	}

	public static void draw(int srcx, int srcy, int dstx, int dsty, ArrayList<Point> elbows) {
		Point temp;
		temp = new Point(srcx, srcy);
		for (Point elbow : elbows) {
			Line.draw(temp.x, temp.y, elbow.x, elbow.y, false);
			temp = elbow;
		}
		Line.draw(temp.x, temp.y, dstx, dsty, true);
	}

	@Override
	public void renderNormal() {
		getCanvas().setFgColor(0, 0, 0);
		getCanvas().setBgColor(255, 255, 255);

		generateSrcDstPoints();
		draw(getSrcx(), getSrcy(), getDstx(), getDsty(), elbows);

		/* Determine max text width from each line */
		String text = getText();
		String[] lines = text.split("\r\n|\r|\n");
		int textWidth = -1;
		for (String line : lines) {
			int[] extent = getCanvas().stringExtent(line);
			if (extent[0] > textWidth)
				textWidth = extent[0];
		}

		/* Determine text height of lines */
		int textHeight = getCanvas().stringExtent(text)[1] * lines.length;

		getCanvas().setBgColor(255, 255, 255);
		String temp = text;
		if (!temp.equals("")) {
			temp = " " + temp + " ";
		}
		int middlex = getSrcx() * 5 + getDstx() * 4;
		int middley = getSrcy() * 5 + getDsty() * 4;
		for (Point elbow : elbows) {
			middlex += elbow.x;
			middley += elbow.y;
		}
		middlex /= (9 + elbows.size());
		middley /= (9 + elbows.size());
		getCanvas().drawText(temp, middlex - textWidth / 2, middley - textHeight / 2);
	}

	@Override
	public IElement checkBoundary(int x, int y) {
		IElement retval = super.checkBoundary(x, y);
		if (retval == null || retval instanceof AEditable) {
			Point temp = new Point(getSrcx(), getSrcy());
			for (Point elbow : elbows) {
				if (checkBoundary(x, y, temp, elbow)) {
					return this;
				}
				temp = elbow;
			}
			if (checkBoundary(x, y, temp, new Point(getDstx(), getDsty()))) {
				return this;
			}
		}
		return retval;
	}

	@Override
	protected void generateSrcDstPoints() {
		if (elbows.isEmpty()) {
			super.generateSrcDstPoints();
		} else {
			ArrayList<Point> srcPoints;
			srcPoints = new ArrayList<>();
			int srcx = getSrcElement().getX();
			int srcy = getSrcElement().getY();
			int srcw = getSrcElement().getWidth();
			int srch = getSrcElement().getHeight();
			srcPoints.add(new Point(srcx + srcw / 2, srcy));
			srcPoints.add(new Point(srcx, srcy + srch / 2));
			srcPoints.add(new Point(srcx + srcw / 2, srcy + srch));
			srcPoints.add(new Point(srcx + srcw, srcy + srch / 2));

			ArrayList<Point> dstPoints;
			dstPoints = new ArrayList<>();
			int dstx = getDstElement().getX();
			int dsty = getDstElement().getY();
			int dstw = getDstElement().getWidth();
			int dsth = getDstElement().getHeight();
			dstPoints.add(new Point(dstx + dstw / 2, dsty));
			dstPoints.add(new Point(dstx, dsty + dsth / 2));
			dstPoints.add(new Point(dstx + dstw / 2, dsty + dsth));
			dstPoints.add(new Point(dstx + dstw, dsty + dsth / 2));

			double min;

			min = Double.MAX_VALUE;
			srcx = 0;
			srcy = 0;

			for (int i = 0; i < 4; i++) {
				double euc = 0;
				euc += Math.pow(srcPoints.get(i).x - elbows.get(0).x, 2);
				euc += Math.pow(srcPoints.get(i).y - elbows.get(0).y, 2);
				euc = Math.sqrt(euc);
				if (euc < min) {
					srcx = srcPoints.get(i).x;
					srcy = srcPoints.get(i).y;
					min = euc;
				}
			}

			min = Double.MAX_VALUE;
			dstx = 0;
			dsty = 0;

			int last = elbows.size() - 1;
			for (int i = 0; i < 4; i++) {
				double euc = 0;
				euc += Math.pow(dstPoints.get(i).x - elbows.get(last).x, 2);
				euc += Math.pow(dstPoints.get(i).y - elbows.get(last).y, 2);
				euc = Math.sqrt(euc);
				if (euc < min) {
					dstx = dstPoints.get(i).x;
					dsty = dstPoints.get(i).y;
					min = euc;
				}
			}

			setSrcx(srcx);
			setSrcy(srcy);
			setDstx(dstx);
			setDsty(dsty);
		}
	}

	public ArrayList<Point> getElbows() {
		return elbows;
	}

	public void addElbow(Point elbow) {
		elbows.add(elbow);
	}

	public void resetElbows() {
		elbows.clear();
	}

	@Override
	public void createEditPoints() {
		ArrayList<Point> points = new ArrayList<>();
		points.add(new Point(getSrcx(), getSrcy()));
		points.add(new Point(getDstx(), getDsty()));
		for (Point elbow : elbows) {
			points.add(elbow);
		}
		createEditPoints(points);
	}

	@Override
	public JSONObject jsonEncode() {
		JSONObject obj = super.jsonEncode();
		JSONArray elbows = new JSONArray();
		for (Point elbow : this.elbows) {
			JSONObject temp = new JSONObject();
			temp.put("x", elbow.x);
			temp.put("y", elbow.y);
			elbows.put(temp);
		}
		obj.put("elbows", elbows);
		return obj;
	}

	@Override
	public void jsonDecode(JSONObject obj) {
		super.jsonDecode(obj);
		JSONArray elbows = obj.getJSONArray("elbows");
		for (int i = 0; i < elbows.length(); i++) {
			JSONObject elbow = elbows.getJSONObject(i);
			Point p = new Point(elbow.getInt("x"), elbow.getInt("y"));
			addElbow(p);
		}
	}

	@Override
	public void drag(int x, int y, IElement e) {
		if (e instanceof EditPoint) {
			int code = ((EditPoint) e).getCode();
			if (code == EditPoint.BEGIN) {
				// src edit point
			} else if (code == EditPoint.END) {
				// dst edit point
			} else {
				code = code - 2;
				Point p = getElbows().get(code);
				p.x = x;
				p.y = y;
			}
		}
		super.drag(x, y, e);
	}

	@Override
	public void drag(int x, int y) {
		if (getElbows().isEmpty()) {
			return;
		}
		for (Point elbow : getElbows()) {
			elbow.x = elbow.x + x;
			elbow.y = elbow.y + y;
		}
	}

}

package diagram.element;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
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

	public static void draw(GC gc, int srcx, int srcy, int dstx, int dsty, ArrayList<Point> elbows) {
		Point temp;
		temp = new Point(srcx, srcy);
		for (Point elbow : elbows) {
			Line.draw(gc, temp.x, temp.y, elbow.x, elbow.y, false);
			temp = elbow;
		}
		Line.draw(gc, temp.x, temp.y, dstx, dsty, true);
	}

	@Override
	public void renderNormal() {
		GC gc = new GC(getCanvas());
		Color black = new Color(gc.getDevice(), 0, 0, 0);
		Color white = new Color(gc.getDevice(), 255, 255, 255);
		gc.setForeground(black);
		gc.setBackground(black);

		generateSrcDstPoints();
		draw(gc, getSrcx(), getSrcy(), getDstx(), getDsty(), elbows);

		/* Determine max text width from each line */
		String text = getText();
		String[] lines = text.split("\r\n|\r|\n");
		int textWidth = -1;
		for (String line : lines) {
			if (gc.stringExtent(line).x > textWidth)
				textWidth = gc.stringExtent(line).x;
		}

		/* Determine text height of lines */
		int textHeight = gc.stringExtent(text).y * lines.length;

		gc.setBackground(white);
		String temp = text;
		if (!temp.equals("")) {
			temp = " " + temp + " ";
		}
		int middlex = (getSrcx() + getDstx()) / 2;
		int middley = (getSrcy() + getDsty()) / 2;
		gc.drawText(temp, middlex - textWidth / 2, middley - textHeight / 2);

		gc.dispose();
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
	public void drag(int x1, int y1, int x2, int y2, IElement e) {
		if (e instanceof EditPoint) {
			int code = ((EditPoint) e).getCode();
			if (code == EditPoint.BEGIN) {
				// src edit point
			} else if (code == EditPoint.END) {
				// dst edit point
			} else {
				code = code - 2;
				Point p = getElbows().get(code);
				p.x = x2;
				p.y = y2;
			}
		}
		super.drag(x1, y1, x2, y2, e);
	}

}

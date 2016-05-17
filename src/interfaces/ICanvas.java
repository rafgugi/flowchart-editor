package interfaces;

public interface ICanvas {
	public void setFgColor(int r, int g, int b);
	public void setBgColor(int r, int g, int b);
	public void clear();
	public void drawLine(int srcx, int srcy, int dstx, int dsty);
	public void drawOval(int x, int y, int w, int h);
	public void fillOval(int x, int y, int w, int h);
	public void drawPolygon(int[] points);
	public void fillPolygon(int[] points);
	public void drawRectangle(int x, int y, int w, int h);
	public void fillRectangle(int x, int y, int w, int h);
	public void drawRoundRectangle(int x, int y, int w, int h, int radX, int radY);
	public void fillRoundRectangle(int x, int y, int w, int h, int radX, int radY);
	public void drawText(String text, int x, int y);
	public int[] stringExtent(String text);
	public int getTranslateX();
	public int getTranslateY();
}

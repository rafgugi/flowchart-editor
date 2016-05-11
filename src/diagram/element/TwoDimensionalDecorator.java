package diagram.element;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.json.JSONObject;

import exception.PersistenceException;
import interfaces.IDrawingState;
import interfaces.IElement;

public abstract class TwoDimensionalDecorator extends TwoDimensional {

	private TwoDimensional component;
	
	public TwoDimensionalDecorator() {
	}

	public TwoDimensionalDecorator(TwoDimensional component) {
		this.component = component;
	}

	public TwoDimensional getComponent() {
		return component;
	}

	@Override
	public void renderNormal() {
		// can't render :(
	}

	@Override
	public void draw() {
		component.getState().draw(component);
	}

	@Override
	public void select() {
		component.select();
	}

	@Override
	public void deselect() {
		component.deselect();
	}

	// @Override
	// public IElement checkBoundary(int x, int y) {
	// 	return component.checkBoundary(x, y);
	// }

	// @Override
	// public IElement checkBoundary(int x1, int y1, int x2, int y2) {
	// 	return component.checkBoundary(x1, y1, x2, y2);
	// }

	@Override
	public int getX() {
		return component.getX();
	}

	@Override
	public void setX(int x) {
		component.setX(x);
	}

	@Override
	public int getY() {
		return component.getY();
	}

	@Override
	public void setY(int y) {
		component.setY(y);
	}

	@Override
	public int getWidth() {
		return component.getWidth();
	}

	@Override
	public void setWidth(int width) {
		component.setWidth(width);
	}

	@Override
	public int getHeight() {
		return component.getHeight();
	}

	@Override
	public void setHeight(int height) {
		component.setHeight(height);
	}

	@Override
	public String getText() {
		return component.getText();
	}

	@Override
	public void setText(String text) {
		component.setText(text);
	}

	@Override
	public void setLocation(int x, int y) {
		component.setLocation(x, y);
	}

	@Override
	public void setBoundary(int x1, int y1, int x2, int y2) {
		component.setBoundary(x1, y1, x2, y2);
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2) {
		component.drag(x1, y1, x2, y2);
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2, IElement e) {
		component.drag(x1, y1, x2, y2, e);
	}

	@Override
	public IDrawingState getState() {
		return component.getState();
	}

	@Override
	public boolean isActive() {
		return component.isActive();
	}

	// @Override
	// public ArrayList<IElement> getConnectedElements() {
	// 	return component.getConnectedElements();
	// }

	// @Override
	// public void connect(IElement element) {
	// 	component.connect(element);
	// }

	// @Override
	// public void disconnect(IElement element) {
	// 	component.disconnect(element);
	// }

	// @Override
	// public ArrayList<TwoDimensional> getChildren() {
	// 	return component.getChildren();
	// }

	// @Override
	// public ArrayList<TwoDimensional> getParents() {
	// 	return component.getParents();
	// }

	@Override
	public UUID getId() {
		return component.getId();
	}

	@Override
	public JSONObject jsonEncode() {
		JSONObject obj = new JSONObject();
		obj.put("class", getClass().getName());
		obj.put("id", super.getId());
		JSONObject comJson = component.jsonEncode();
		obj.put("component", comJson);
		return obj;
	}

	@Override
	public void jsonDecode(JSONObject obj) {
		JSONObject comJson = obj.getJSONObject("component");

		String className = comJson.getString("class");
		IElement elem = null;
		try {
			elem = (IElement) Class.forName(className).getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			throw new PersistenceException("Create element: " + e.getMessage());
		}
		if (elem == null) {
			throw new PersistenceException("Wrong JSON format: wrong class name.");
		}
		elem.jsonDecode(comJson);
		
		component = (TwoDimensional) elem;
	}

}

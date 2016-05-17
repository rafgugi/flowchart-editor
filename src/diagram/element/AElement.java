package diagram.element;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONObject;

import diagram.state.EditState;
import diagram.state.NormalState;
import interfaces.ICanvas;
import interfaces.IDrawingState;
import interfaces.IElement;
import main.Main;
import widget.editor.SubEditor;
import widget.window.MainWindow;

public abstract class AElement implements IElement {
	
	private IDrawingState state;
	private ArrayList<IElement> connected = new ArrayList<>();
	private UUID id;

	public AElement() {
		state = NormalState.getInstance();
		id = UUID.randomUUID();
	}

	protected static ICanvas getCanvas() {
		SubEditor s = (SubEditor) MainWindow.getInstance().getEditor().getActiveSubEditor(); 
		return s.getCanvas();
	}

	@Override
	public void select() {
		if (state == EditState.getInstance()) {
			return;
		}
		// Main.log("Select " + this.toString());
		state = EditState.getInstance();
	}

	@Override
	public void deselect() {
		if (!isActive()) {
			return;
		}
		// Main.log("Deselect " + this.toString());
		state = NormalState.getInstance();
	}

	@Override
	public IDrawingState getState() {
		return state;
	}

	@Override
	public boolean isActive() {
		return getState() != NormalState.getInstance();
	}

	@Override
	public void draw() {
		state.draw(this);
	}

	@Override
	public void drag(int x1, int y1, int x2, int y2) {
		deselect();
		select();
	}

	@Override
	public ArrayList<IElement> getConnectedElements() {
		return connected;
	}

	@Override
	public void connect(IElement element) {
		connected.add(element);
	}

	@Override
	public void disconnect(IElement element) {
		if (!connected.remove(element)) {
			Main.log("Element not found when trying to disconnect");
			Main.log("    " + this);
			Main.log("    " + element);
		}
	}

	@Override
	public UUID getId() {
		return id;
	}

	/**
	 * Set id.
	 * 
	 * @param id
	 */
	private void setId(UUID id) {
		this.id = id;
	}

	/**
	 * Set id.
	 * 
	 * @param id
	 */
	private void setId(String id) {
		setId(UUID.fromString(id));
	}

	@Override
	public JSONObject jsonEncode() {
		JSONObject obj = new JSONObject();
		obj.put("class", getClass().getName());
		obj.put("id", getId());
		return obj;
	}

	@Override
	public void jsonDecode(JSONObject obj) {
		setId(obj.getString("id"));
	}

	@Override
	public String toString() {
		return "[" + getId().getLeastSignificantBits() + "] " + getClass().getName();
	}

}

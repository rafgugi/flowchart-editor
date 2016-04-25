package interfaces;

import org.json.JSONObject;

public interface JSONAble {
	public JSONObject jsonEncode();
	public void jsonDecode(JSONObject obj);
}

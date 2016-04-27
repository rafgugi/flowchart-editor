package interfaces;

import org.json.JSONObject;

/**
 * All things that you want it to be saved as json must 
 * implement this. Object that implement this must have
 * constructor with no parameter.
 */
public interface JSONAble {

	/**
	 * Create JSONObject explaining the details of
	 * this object.
	 *
	 * @return This object as JSONObject
	 */
	public JSONObject jsonEncode();

	/**
	 * Populate object property with details in JSONObject.
	 *
	 * @param JSONObject that previously generated.
	 */
	public void jsonDecode(JSONObject obj);
}

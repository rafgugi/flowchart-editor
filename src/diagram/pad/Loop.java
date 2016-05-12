package diagram.pad;

/**
 * Single loop has exactly one child layer. Also have flow type, defining 
 * from which flow (either "yes" or "no") the child is. If the child is from
 * "no", then add "!" before the text expression.
 */
public abstract class Loop extends BlockSingle {

	private BlockContainer child;
	private boolean flowType;

	/**
	 * Get child block container.
	 * 
	 * @return child
	 */
	public BlockContainer getChild() {
		return child;
	}

	/**
	 * Set child block container.
	 * 
	 * @param child
	 */
	public void setChild(BlockContainer child) {
		this.child = child;
	}

	/**
	 * Get flow type, either true or false.
	 * 
	 * @return flowType
	 */
	public boolean getFlowType() {
		return flowType;
	}

	/**
	 * Set flow type.
	 * 
	 * @param flowType
	 */
	public void setFlowType(boolean flowType) {
		this.flowType = flowType;
	}

	@Override
	public String getText() {
		if (getFlowType()) {
			return super.getText();
		} else {
			return "!(" + super.getText() + ")";
		}
	}

}

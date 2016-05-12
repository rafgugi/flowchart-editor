package diagram.pad;

import java.util.ArrayList;

import exception.GenerateCodeException;

public class Selection extends BlockSingle {

	private ArrayList<BlockContainer> children = new ArrayList<>();

	public ArrayList<BlockContainer> getChildren() {
		return children;
	}

	public void addChild(BlockContainer child) {
		children.add(child);
	}

	public String generate() {
		throw new GenerateCodeException("Not implemented yet.");
	}

}

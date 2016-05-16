package command;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.ANTLRInputStream;

import antlr.CLexer;
import antlr.CParser;
import interfaces.ICommand;
import main.Main;

/**
 * This command must receive input code to be parsed, then parse
 * into tree when executed.
 */
public class SourceCodeTreeCommand implements ICommand {

	private String input;
	private ParseTree tree;

	public SourceCodeTreeCommand(String input) {
		this.input = input;
	}

	@Override
	public void execute() {
		ANTLRInputStream in = new ANTLRInputStream(input);
		CLexer lexer = new CLexer(in);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CParser parser = new CParser(tokens);
		tree = parser.compilationUnit();
		Main.log(tree.getText());
	}

	public String getString() {
		return input;
	}

	public ParseTree getTree() {
		return tree;
	}

}

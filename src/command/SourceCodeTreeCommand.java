package command;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;

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
		CLexer lexer = new CLexer(new ANTLRInputStream(input));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CParser parser = new CParser(tokens);
		parser.addErrorListener(new BaseErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPosition, String msg, RecognitionException e) {
				Main.log("Error");
			}
		});
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

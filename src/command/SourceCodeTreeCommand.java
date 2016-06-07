package command;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;

import antlr.CLexer;
import antlr.CParser;
import exception.SyntaxErrorException;
import interfaces.ICommand;

/**
 * This command must receive input code to be parsed, then parse into tree when
 * executed.
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
			public void syntaxError(Recognizer<?, ?> r, Object sym, int line, int row, String msg,
					RecognitionException e) {
				throw new SyntaxErrorException(msg, line, row);
			}
		});
		ParseTree tree = null;
//		Main.log("abstractDeclarator");
//		tree = parser.abstractDeclarator();
//		Main.log("compoundStatement");
//		tree = parser.compoundStatement();
//		Main.log("compilationUnit");
//		tree = parser.compilationUnit();
		this.tree = tree;
	}

	public String getString() {
		return input;
	}

	public ParseTree getTree() {
		return tree;
	}

}

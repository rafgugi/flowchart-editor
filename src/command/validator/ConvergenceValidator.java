package command.validator;

/**
 * 1. Judgment doesn't have convergence
 * 2. Too much convergence
 */
public class ConvergenceValidator extends AValidator {

	@Override
	public void execute() {
		/* Dari atas, klo ketemu judgment masukin ke stack.
		 * klo ketemu convergence, pasangkan stack.pop. Jika
		 * stack.pop error, brarti convergence kbanyakan. Jika sampe
		 * akhir masih ada isi stack, brarti kurang convergence.
		 */
	}

}

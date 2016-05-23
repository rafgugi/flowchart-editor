
/* Begin code flowchartElement, send father, his son, and new code */
father = terminator start;
son = father.getSon();
newCode = new NodeCode(); // (0,0)
father.setNodeCode(newCode);
codeCounter = 1;
stackOfJudgment = new Stack<>();
doWhileCounter = 0;
codeAlgorithm(father, son, father.getNodeCode().createSibling());

/**
 * Generate a code for every node according to the coding strategy.
 * 
 * @param fatherNode
 * @param currentNode
 * @param currentCode
 */
public void codeAlgorithm(FlowChartElement father, FlowChartElement currElem, NodeCode currCode) {
    if (currElem is Terminator) {
        return; // exit point of recursion
    }
    if (currElem is Process) {
        if (currElem.hasntBeenTraversed() || currElem.getDoWhileCounter() < doWhileCounter) {
            if (currElem.getDoWhileCounter() < doWhileCounter) {
                /* This process is still surrounded by do-while */
                currElem.increaseDoWhileCounter();
            } else {
                /* Process hasn't been traversed */
                currElem.traverse();
            }
            currElem.setNodeCode(currCode);
            son = currElem.getSon();

            /* Generate the code for sibling */
            codeOfSon = currCode.createSibling();
            codeAlgorithm(currElem, son, codeOfSon);
        } else {
            /*
             * Father is recognized as a do-while structure, mark the
             * Judgment and link it with his Father, include do-while and
             * nested do-while
             */
            if (father is Judgment && father.getType() == null
                    || currElem.getDoWhileCounter() < doWhileCounter) {
                if (currElem.getDoWhileCounter() < doWhileCounter) {
                    /* This process is still surrounded by do-while */
                    currElem.increaseDoWhileCounter();
                }
                /* Process's father is do-while */
                father.setType("DoWhile");
                thisCode = currElem.getNodeCode();
                father.setNodeCode(thisCode); // do-while take over child code
                thisCode.resetChildren(); // and reset the child of the code

                /* Begining to recode do-while child */
                doWhileCounter++; // increase do-while stack
                father.increaseDoWhileCounter();
                codeAlgorithm(father, currElem, thisCode.createChild());
                doWhileCounter--; // reset do-while stack after finish recode
            }
        }
    }
    if (currElem is Judgment) {
        if (currElem.hasntBeenTraversed() || currElem.getDoWhileCounter() < doWhileCounter) {
            if (currElem.getDoWhileCounter() < doWhileCounter) {
                /* This judgment is still surrounded by do-while */
                currElem.increaseDoWhileCounter();
            } else {
                 /* Judgment hasn't been traversed */
                currElem.traverse();
            }
            currElem.setNodeCode(currCode);

            if (currElem.getDirectConvergence() == null) {
                /* Push into stackOfJudgment, to find convergence */
                stackOfJudgment.push(currElem);
            }

            /* Get direct convergence and process other children */
            convergenceson = null
            for (son : currElem.getSons()) {
                if (son is Convergence) {
                    convergenceSon = son;
                    continue;
                }
                /* Go to judgment's child that is not convergence */
                sonCode = currCode.createChild();
                codeAlgorithm(currElem, son, sonCode);
            }

            if (convergenceson != null) {
                /* Go to judgment's direct convergence child */
                codeAlgorithm(currElem, convergenceson, null);
            }

            /* loop structures have been recognized, the left is selections */
            if (currElem.getType() == null) {
                /*
                 * Kan sebelumnya udah ngakses children nya. Kalau sampe udah
                 * keluar dari children dan node ini belum ketahuan tipenya,
                 * berarti ini selection.
                 */
                currElem.setType("Selection");
            }

            /* Go to the elements behind Convergence. */
            convergence = currElem.getDirectConvergence();
            sonCode = convergence.getNodeCode().createSibling();
            sonNode = convergence.getSon();
            /* Go to judgment's direct convergence's child */
            codeAlgorithm(convergence, sonNode, sonCode);
        }
        else { // been traversed.
            if (currElem.getType() == null) {
                currElem.setType("While");
            }
            else { // and been recognized
                if (father is Judgment && father.getType() == null
                        || currElem.getDoWhileCounter() < doWhileCounter) {
                    if (currElem.getDoWhileCounter() < doWhileCounter) {
                        /* This process is still surrounded by do-while */
                        currElem.increaseDoWhileCounter();
                    }
                    /* Judgment father is do-while */
                    father.setType("DoWhile");
                    thisCode = currElem.getNodeCode();
                    father.setNodeCode(thisCode); // do-while take over child code
                    thisCode.resetChildren(); // and reset the child of the code

                    /* Begining to recode do-while child */
                    doWhileCounter++; // increase do-while stack
                    father.increaseDoWhileCounter();
                    codeAlgorithm(father, currElem, thisCode.createChild());
                    doWhileCounter--; // reset do-while stack after finish recode
                }
            }
        }
    }
    if (currElem is Convergence) {
        /* match a judgment node and a convergence node */
        if (currElem.hasntBeenTraversed() || currElem.getDoWhileCounter() < doWhileCounter) {
            if (currElem.getDoWhileCounter() < doWhileCounter) {
                /* This process is still surrounded by do-while */
                currElem.increaseDoWhileCounter();
            } else {
                /* Convergence hasn't been traversed */
                currElem.traverse();
            }

            /* as a judgment and a convergence is exist geminate, so the top
             * judgment in stack must be able to match the current
             * convergence
             */
            if (currElem.getDirectJudgment() == null) {
                /* Connect convergence with judgment */
                judgment = stackOfJudgment.pop();
                currElem.setDirectJudgment(judgment);
                judgment.setDirectConvergence(currElem);
            }
            /* Set this convergence node code from direct judgment */
            currElem.setNodeCode(currElem.getDirectJudgment().getNodeCode());
        } else {
            /* Convergence has been traversed */
            currElem.setNodeCode(currElem.getDirectJudgment().getNodeCode());
            return;
        }
    }
}
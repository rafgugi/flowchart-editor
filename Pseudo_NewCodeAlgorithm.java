
/* Begin code flowchartElement, send father, his son, and new code */
father = terminator start;
son = father.getFlow().getSon();
newCode = new NodeCode(); // (0,0)
father.setNodeCode(newCode);
stackOfJudgment = new Stack<>();
doWhileStack = new Stack<>();
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

  if (!doWhileStack.empty() && currElem.getDoWhileNode() != doWhileStack.peek()) {
    /* This process is still surrounded by do-while, reset traversed */
    currElem.resetTraversed();
    currElem.setDoWhileNode(doWhileStack.peek());
  }
  if (currElem is Process) {
    if (currElem.isntTraversed()) {
      /* Process hasn't been traversed */
      currElem.traverse();
      currElem.setNodeCode(currCode);
      son = currElem.getFlow().getSon();

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
          || father.getType() == "DoWhile") {
        /* Process's father is do-while */
        father.setType("DoWhile");
        thisCode = currElem.getNodeCode();
        /* do-while take over child code */
        father.setNodeCode(thisCode);
        /* and reset the child of the code */
        thisCode.clearChildren();

        /* Begining to recode do-while child */
        doWhileStack.push(father); // push doWhile to stack
        father.setDoWhileNode(doWhileStack.peek());
        flowCode = father.getFlow(currElem).getCode();
        childCode = thisCode.createChild(flowCode);
        codeAlgorithm(father, currElem, childCode);
        doWhileStack.pop(); // pop doWhile after finish recode
      }
    }
  }
  if (currElem is Judgment) {
    if (currElem.isntTraversed()) {
       /* Judgment hasn't been traversed */
      currElem.traverse();
      currElem.setNodeCode(currCode);

      if (currElem.getDirectConvergence() == null) {
        /* Push into stackOfJudgment, to find convergence */
        stackOfJudgment.push(currElem);
      }

      /* Get direct convergence and process other children */
      convergenceson = null
      for (flow : currElem.getFlows()) {
        son = flow.getSon();
        if (son is Convergence) {
          convergenceSon = son;
          continue;
        }
        /* Go to judgment's child that is not convergence */
        flowCode = flow.getCode();
        sonCode = currCode.createChild(flowCode);
        codeAlgorithm(currElem, son, sonCode);
      }

      if (convergenceson != null) {
        /* Go to judgment's direct convergence child */
        codeAlgorithm(currElem, convergenceson, null);
      }

      /* loop structures have been recognized, the left is selections */
      if (currElem.getType() == null) {
        currElem.setType("Selection");
      }

      /* Go to the element behind Convergence */
      convergence = currElem.getDirectConvergence();
      sonCode = convergence.getNodeCode().createSibling();
      sonNode = convergence.getFlow().getSon();
      codeAlgorithm(convergence, sonNode, sonCode);
    }
    else { // been traversed.
      if (currElem.getType() == null) {
        currElem.setType("While");
      }
      else { // and been recognized
        if (father is Judgment && father.getType() == null) {
          /* Judgment father is do-while */
          father.setType("DoWhile");
          thisCode = currElem.getNodeCode();
          /* do-while take over child code */
          father.setNodeCode(thisCode);
          /* and reset the child of the code */
          thisCode.clearChildren();

          /* Begining to recode do-while child */
          doWhileStack.push(father); // push doWhile to stack
          father.setDoWhileNode(doWhileStack.peek());
          flowCode = father.getFlow(currElem).getCode();
          childCode = thisCode.createChild(flowCode);
          codeAlgorithm(father, currElem, childCode);
          doWhileStack.pop(); // pop doWhile after finish recode
        }
      }
    }
  }
  if (currElem is Convergence) {
    /* match a judgment node and a convergence node */
    if (currElem.isntTraversed()) {
      /* Convergence hasn't been traversed */
      currElem.traverse();

      /* as a judgment and a convergence is exist geminate, so the
       * top judgment in stack must be able to match the current
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
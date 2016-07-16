/* Begin code flowchartElement, send father, his son, and new code */
father = terminator start;
son = father.getFlow().getSon();
newCode = new NodeCode(); // (0,0)
set nodeCode of father with newCode
stackOfJudgment = new Stack<>();
doWhileStack = new Stack<>();
codeAlgorithm(father, son, father.getNodeCode().createSibling());

public void codeAlgorithm(FlowChartElement father, FlowChartElement currElem, NodeCode currCode) {
  if (currElem is Terminator) {
    return; // exit point of recursion
  }

  if (currElem.getDoWhileNode() != doWhileStack.peek()) {
    reset traversed of currElem // masih butuh recode
  }
  if (currElem is Process) {
    if (currElem isntTraversed) {
      traverse currElem
      set nodeCode of currElem with currCode
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
      if (father is Judgment && father type not defined or DoWhile) {
        /* Process's father is do-while */
        set type of father with DoWhile
        thisCode = node code of currElem
        /* do-while take over child code */
        set nodeCode of father with thisCode
        /* and reset the child of the code */
        thisCode.clearChildren();

        /* Begining to recode do-while child */
        doWhileStack.push(father); // push doWhile to stack
        father.setDoWhileNode(doWhileStack.peek());
        childCode = create child of thisCode;
        codeAlgorithm(father, currElem, childCode);
        doWhileStack.pop; // pop doWhile after finish recode
      }
    }
  }
  if (currElem is Judgment) {
    if (currElem isntTraversed) {
       /* Judgment hasn't been traversed */
      traverse currElem
      set nodeCode of currElem with currCode

      if (currElem.getDirectConvergence() == null) {
        /* Push into stackOfJudgment, to find convergence */
        stackOfJudgment.push(currElem);
      }

      /* Get direct convergence and process other children */
      convergenceson = null
      for each (sons of currElem as son) {
        if (son is Convergence) {
          convergenceSon = son;
          continue;
        }
        /* Go to judgment's child that is not convergence */
        sonCode = create child of currCode;
        codeAlgorithm(currElem, son, sonCode);
      }

      if (convergenceson != null) {
        /* Go to judgment's direct convergence child */
        codeAlgorithm(currElem, convergenceson, null);
      }

      /* loop structures have been recognized, the left is selections */
      if (currElem.getType() == null) {
        set type of father with Selection
      }

      /* Go to the element behind Convergence */
      convergence = directConvergence of currElem
      sonCode = create sibling from node code of convergence
      sonNode = son of convergence
      codeAlgorithm(convergence, sonNode, sonCode);
    }
    else { // been traversed.
      if (currElem.getType() == null) {
        set type of father with While
      }
      else { // and been recognized
        if (father is Judgment && father.getType() == null) {
          /* Judgment father is do-while */
          set type of father with DoWhile
          thisCode = node code of currElem
          /* do-while take over child code */
          set nodeCode of father with thisCode
          /* and reset the child of the code */
          thisCode.clearChildren();

          /* Begining to recode do-while child */
          doWhileStack.push(father); // push doWhile to stack
          father.setDoWhileNode(doWhileStack.peek());
          childCode = create child of thisCode;
          codeAlgorithm(father, currElem, childCode);
          doWhileStack.pop; // pop doWhile after finish recode
        }
      }
    }
  }
  if (currElem is Convergence) {
    /* match a judgment node and a convergence node */
    if (currElem isntTraversed) {
      /* Convergence hasn't been traversed */
      traverse currElem

      if (currElem doesnt have directJudgment) {
        /* Connect convergence with judgment */
        judgment = stackOfJudgment.pop;
        set direct judgment of currElem with judgment
        set direct convergence of judgment with currElem
      }
      /* Set this convergence node code from direct judgment */
    }
    set nodeCode of currElem with node code of currElem directJudgment
  }
}
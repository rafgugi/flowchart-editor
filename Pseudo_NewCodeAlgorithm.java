/* Begin code flowchartElement, send father, his son, and new code */
father = terminator start;
son = father.getFlow().getSon();
newCode = new NodeCode(); // (0,0)
set nodeCode of father with newCode
stackOfJudgment = new Stack<>();
doWhileStack = new Stack<>();
codeAlgorithm(father, son, father.getNodeCode().createSibling());

function codeAlgorithm(FlowChartElement father, FlowChartElement currElem, NodeCode currCode) 
  if (currElem is Terminator) 
    return; // exit point of recursion

  if (currElem.getDoWhileNode() != doWhileStack.peek) 
    reset traversed of currElem // masih butuh recode

  if (currElem is Process) 
    if (currElem isntTraversed) 
      traverse currElem
      set nodeCode of currElem with currCode
      son = currElem.getFlow().getSon();

      codeOfSon = currCode.createSibling();
      codeAlgorithm(currElem, son, codeOfSon);

      if (father is Judgment && father type not defined or DoWhile) 
        set type of father with DoWhile
        thisCode = node code of currElem
        set nodeCode of father with thisCode
        thisCode.clearChildren();

        doWhileStack.push(father);
        father.setDoWhileNode(doWhileStack);
        childCode = create child of thisCode;
        codeAlgorithm(father, currElem, childCode);
        doWhileStack.pop;

  if (currElem is Judgment) 
    if (currElem isntTraversed) 
      traverse currElem
      set nodeCode of currElem with currCode

      if (currElem.getDirectConvergence() == null) 
        stackOfJudgment.push(currElem);

      convergenceson = null
      for each (sons of currElem as son) 
        if (son is Convergence) 
          convergenceSon = son;
          continue;

        sonCode = create child of currCode;
        codeAlgorithm(currElem, son, sonCode);

      if (convergenceson != null) 
        codeAlgorithm(currElem, convergenceson, null);

      if (currElem.getType == null) 
        set type of father with Selection

      convergence = directConvergence of currElem
      sonCode = create sibling from node code of convergence
      sonNode = son of convergence
      codeAlgorithm(convergence, sonNode, sonCode);

    else  // been traversed.
      if (currElem.getType == null) 
        set type of father with While
      else  // and been recognized
        if (father is Judgment && father.getType == null) 
          set type of father with DoWhile
          thisCode = node code of currElem
          set nodeCode of father with thisCode
          thisCode.clearChildren();

          doWhileStack.push(father);
          father.setDoWhileNode(doWhileStack);
          childCode = create child of thisCode;
          codeAlgorithm(father, currElem, childCode);
          doWhileStack.pop;

  if (currElem is Convergence) 
    if (currElem isntTraversed) 
      traverse currElem

      if (currElem doesnt have directJudgment) 
        judgment = stackOfJudgment.pop;
        set direct judgment of currElem with judgment
        set direct convergence of judgment with currElem
    set nodeCode of currElem with node code of currElem directJudgment
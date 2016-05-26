
/* Get first (0,0) code from previous algorithm */
firstCode = codeAlgorithm.getFirstCode();
fatherBlock = new BlockContainer();
convertToPAD(firstCode, fatherBlock);

/**
 * Convert the coded flowchart into PAD.
 * 
 * @param currCode
 * @param fatherBlock
 */
public void convertToPAD(NodeCode currCode, BlockContainer fatherBlock) {
  if (currCode == null) {
    return;
  }
  currElem = currCode.getElement();
  if (currElem == null) {
    return;
  }

  if (currElem.getType() is "Process") {
    element = new Sequence();
    element.setText(currElem.getText());
    fatherBlock.addElement(element);
  }
  if (currElem.getType() is "Selection") {
    element = new Selection();
    element.setText(currElem.getText());

    /* Create new layer, traverse
     * each child flow which is not convergence */
    for (child : currCode.getChildren()) {
      nextFlow = child.getElement();
      if (nextFlow is Convergence) {
        continue;
      }
      subBlock = new BlockContainer();
      if (child.getX() == 1) { // from YES flow
        element.setYesChild(subBlock);
      } else if (child.getX() == 2) { // from NO flow
        element.setNoChild(subBlock);
      }
      /* Proces each child */
      convertToPAD(nextFlow.getNodeCode(), subBlock);
    }
    fatherBlock.addElement(element);
  }
  if (currElem.getType() is "Loop" OR "While") {
    if (currElem.getType() is "While") {
      element = new While();
    } else {
      element = new DoWhile();
    }
    element.setText(currElem.getText());
    subContainer = new BlockContainer();

    /* Loop element always have only 1 children */
    childCode = currCode.getChildren().get(0);
    /* Set flowType from code's x value */
    element.setFlowType(childCode.getX() == 1);
    element.setChild(subContainer);
    fatherBlock.addElement(element);
    convertToPAD(childCode, subContainer);
  }

  /* Go to next code from the same layer */
  currCode = currCode.getSibling();
  convertToPAD(currCode, fatherBlock);
}
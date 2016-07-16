firstCode = first code from codeAlgorithm;
fatherBlock = new BlockContainer;
convertToPAD(firstCode, fatherBlock);

convertToPAD(currCode, BlockContainer fatherBlock) {
  if (currCode == null) {
    return;
  }
  currElem = currCode.getElement();
  if (currElem == null) {
    return;
  }

  if (currElem type is "Process") {
    element = new Sequence;
    element.setText(currElem.getText());
    add element to fatherBlock;
  }
  if (currElem type is "Selection") {
    element = new Selection;
    element.setText(currElem.getText());

    foreach currCode children thats not convergence {
      nextFlow = child.getElement();
      subBlock = new BlockContainer;
      set subBlock as yes or no child of element;
      convertToPAD(nextFlow.getNodeCode(), subBlock);
    }
    add element to fatherBlock;
  }
  if (currElem type is "DoWhile" OR "While") {
    element = new While or DoWhile;
    element.setText(currElem.getText());
    subContainer = new BlockContainer;

    childCode = the only child of currCode;
    set subContainer as element child
    add element to fatherBlock;
    convertToPAD(childCode, subContainer);
  }

  /* Go to next code from the same layer */
  currCode = currCode next sibling;
  convertToPAD(currCode, fatherBlock);
}
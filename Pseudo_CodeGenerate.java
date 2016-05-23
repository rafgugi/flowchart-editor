/*******************************************************
Function: Generate program code from PAD.
Input: PAD.
Output: Program code.
******************************************************/
/* When pass it into a function, all the operation to it
 * whith that function will effect the original value, as
 * it is address pass*/
String TempCode;
CodeGenerate(beginBlock, TempCode);
CodeGenerate(Block CurrentBlock, String CurrentCode)
{
    if(CurrentBlock.NodeType is Process) {
        CurrentCode.Append(CurrentBlock.codeBlock);
    }
    else if(CurrentBlock.NodeType is Selection)
    {

        Generate branch code as SelectionCode;
        for every SubNode in CurrentBlock.SubNodeList do
        {
            Generate branch code branchCode;
            String branchBody;
            CodeGenerate(SubNode, branchBody);
            Insert branchBody into branchCode;
            SelectionCode.Append(branchCode);
        }
        CurrentBlock.Append(SelectionCode);
    }
    /*If type is HeadPtr, then process the sub-nodes*/
    else if(CurrentBlock.NodeType is HeadPtr)
    {
        for every SubNode in CurrentBlock.SubNodeList do {
            CodeGenerate(SubNode, CurrentCode);
        }
    }
    else if(CurrentBlock.NodeType is Loop)
    {
        Generate loop code as loopCode;
        String loopBody;
        for every SubNode in CurrentBlock.SubNodeList do {
            CodeGenerate(SubNode, loopBody);
        }
        insert loopBody into loopCode;
        CurrentBlock.Append(loopCode);
    }
    else return;
}
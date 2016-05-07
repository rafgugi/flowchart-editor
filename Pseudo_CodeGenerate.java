/*******************************************************
Function: Generate program code from PAD.
Input: PAD.
Output: Program code.
******************************************************/
String TempCode; /*When pass it into a function, all the operation to it whith that function will effect the original value, as it is address pass*/
CodeGenerate(beginBlock, TempCode);
PrintAndFormatCode(TempCode); /*output and format the code*/
CodeGenerate(Block CurrentBlock, String CurrentCode)
{
    if(CurrentBlock.NodeType is Process) CurrentCode.Append(CurrentBlock.codeBlock);
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
    else if(CurrentBlock.NodeType is HeadPtr) /*If type is HeadPtr, then process the sub-nodes*/
    {
        for every SubNode in CurrentBlock.SubNodeList do CodeGenerate(SubNode, CurrentCode);
    }
    else if(CurrentBlock.NodeType is Loop)
    {
        Generate loop code as loopCode;
        String loopBody;
        for every SubNode in CurrentBlock.SubNodeList do CodeGenerate(SubNode, loopBody);
            insert loopBody into loopCode;
        CurrentBlock.Append(loopCode);
    }
    else return;
}
/*************************************************************
Function: Convert the coded flowchart into PAD.
Input: Six hashtables, the coded flowchart.
Output: A PAD.
*************************************************************/
Block beginBlock;
beginBlock.NodeType=Begin;
/*the code of Begin is (0,0), so original code is (0,1)*/
ConvertToPAD(0001, beginBlock);
ConvertToPAD(currentCode, Container fatherBlock)
{
    CurrentNode= GetFromHashTable(currentCode);
    If (CurrentNode is null)) return; /* recursion return condition*/
    Block newBlock;
    If (CurrentNode is Process)
    {
        /*the sequence part of code increase one*/
        currentCode=Increase y of CurrentCode by one;
        newBlock.NodeType=Process;
        newBlock.codeblock=currentNode.Codeblock;
        newBlock.SubNodeList= null;
        fatherBlock.SubNodeList.append(newBlock);
        /*as in the same layer, so the fatherBlock is the same*/
        ConvertToPAD(currentCode, fatherBlock);
    }
    Else If (CurrentNode is Judgment)
    {
        If(CurrentNode.type is selection)
        {
            i=the sum of sons of CurrentNode;
            /*here selectionType represents the types of selection*/
            newBlock.NodeType= selectionType;
            newBlock.codeblock=currentNode.Codeblock;
            fatherBlock.SubNodeList.append(newBlock);
            for(j=0;j<i;j++)
            {
                /*every branch has its owe one*/
                Container selectionHeadPtr;
                selectionHeadPtr.NodeType=HeadPtr;
                newBlock.SubNodeList.append(selectionHeadPtr);
                TempCode =currentCode+(j, 0)； /*nex layer*/
                /*begin a new layer, so the fatherBlock is newBlock */
                ConvertToPAD(currentCode, selectionHeadPtr);
            }
            /* as all the sub-nodes have been coded, so process 
             * the nodes at the same level */
            currentCode=Increase y of CurrentCode by one;
            ConvertToPAD(currentCode, fatherBlock);
        }
        If(CurrentNode.type is loop)
        {
            currentCode =currentCode+(0,0)；/* into next layer */
            /* here loopType represents the types of selection */
            newBlock.NodeType= loopType;
            newBlock.codeblock=currentNode.Codeblock;
            Container newContainer;
            newBlock.append(newContainer);
            /* begin a new layer, so the fatherBlock is newBlock */
            ConvertToPAD(currentCode, newContainer);
            /* as all the sub-nodes have been coded, so process 
             * the nodes at the same level */
            currentCode=Increase y of CurrentCode by one;
            ConvertToPAD(currentCode, fatherBlock);
        }
    }
}
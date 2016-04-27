/***************************************************************************
Function: Recode do-while.
Input: When first enter, the first parameter is the first node in do-while and the second is null.
Output: Recoded flowchart.
***************************************************************************/
Stack LoopReturnStack(Judgment); /*used by recursion return codition */
Recode(Node CurrentNode, String CurrentCode)
{
    if (is not first in) [R1]
    {
        if(CurrentNode.type is loop)
        {
            if (LoopReturnStack.top!= currentNode) Push currentNode into LoopReturnStack; [R2]
            else [R3]
            {
                LoopReturnStack.pop;
                return;
            }
        }
    }
    if (CurrentCode!=null) CurrentNode.Code= CurrentCode; [R4]
    if(CurrentNode.doWhileRecodeCounter>0)
    {
        [R5]
        Get a father (as tfather) of CurrentNode , and meet:
        (tfather.doWhileCounter= doWhileRecodeCounter
            and tfather.doWhileNode=CurrentNode);
        CurrentNode.doWhileRecodeCounter--;
        Recode(tfather, CurrentNode.Code); /*Let the code of tfather be the code of CurrentNode*/ [R6]
        if(CurrentNode.doWhileRecodeCounter==0) [R7]
            CurrentNode.doWhileRecodeCounter= CurrentNode.doWhileCounter; 
    }
    if(CurrentNode.type is Process) [R8]
    {
        CurrentCode←CurrentCode.YY+1; /*the sequence part of code increase one*/
        Recode(CurrentNode.son, CurrentCode,); [R8-1]
    }
    else if(CurrentNode.type is selection)
    {
        for every son j of CurrentNode (except for convergence) do /*j starts from 0*/ [R9]
        {
            CurrentCode =currentCode+0j00； /*nex layer*/
            Recode(son, CurrentCode);
        }
        Recode(CurrentNode.directJudgmentConvergence.son, CurrentCode); [R10]
    }
    else if(CurrentNode.type is loop) [R11]
    {
        CurrentCode =currentCode+0000; /*nex layer*/
        Recode(CurrentNode.son, CurrentCode); /* CurrentNode.son is not the convergence */ [R11-1]
        if(LoopReturnStack.top==null) return; /*return to CodeAlgorithm*/ [R13]
        CurrentCode←CurrentCode.YY+1;
        Recode(CurrentNode.directJudgmentConvergence.son, CurrentCode); [R11-2]
    }
    else return; [R12]
}
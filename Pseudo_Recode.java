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
            if (LoopReturnStack.top!= currentNode){
                Push currentNode into LoopReturnStack; [R2]
            }
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
        Get a father (as tfather) of CurrentNode , where:
        (tfather.doWhileCounter= doWhileRecodeCounter
            and tfather.doWhileNode=CurrentNode);
        CurrentNode.doWhileRecodeCounter--;
        /*Let the code of tfather be the code of CurrentNode*/ [R6]
        Recode(tfather, CurrentNode.Code); 
        if(CurrentNode.doWhileRecodeCounter==0) [R7]
            CurrentNode.doWhileRecodeCounter= CurrentNode.doWhileCounter;
    }
    if(CurrentNode.type is Process) [R8]
    {
         /*the sequence part of code increase one*/
        CurrentCode = Increase y of CurrentCode by one
        Recode(CurrentNode.son, CurrentCode); [R8-1]
    }
    else if(CurrentNode.type is selection)
    {
        /*j starts from 0*/ [R9]
        for every son j of CurrentNode (except for convergence) do
        {
            CurrentCode = currentCode+(j, 0)； /*nex layer*/
            Recode(son, CurrentCode);
        }
        Recode(CurrentNode.directJudgmentConvergence.son, CurrentCode); [R10]
    }
    else if(CurrentNode.type is loop) [R11]
    {
        CurrentCode = CurrentCode+(0, 0); /*nex layer*/
        /* CurrentNode.son is not the convergence */ [R11-1]
        Recode(CurrentNode.son, CurrentCode); 
        /*return to CodeAlgorithm*/ [R13]
        if(LoopReturnStack.top==null) return;
        CurrentCode = Increase y of CurrentCode by one;
        Recode(CurrentNode.directJudgmentConvergence.son, CurrentCode); [R11-2]
    }
    else return; [R12]
}
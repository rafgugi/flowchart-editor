/***************************************************************************
Function: Generate a code for every node according to the coding strategy.
Input: All the nodes of flowchart.
Output: The flowchart coded
****************************************************************************/
/* the elements of this stack is a Judgment, used to match Judgment 
 * and its convergnece */
Stack StackofJudgement(Judgment); 
/* root is Begin node, its code is (0, 0), so the code of first
 * node is (0, 1) */
Node root; 
Let the code of node be a string;
CodeAlgorithm(root, root.son, (0, 0)) /* start up recursion */ [0]
CodeAlgorithm(Node Father, Node CurrentNode, String CurrentCode)
{
    if(CurrentNode is Process) [1]
    {
        CurrentNode.code= CurrentCode /*Generate the code for CurrentNode;*/ [1-1]
        CodeofSon = Increase y of CurrentCode by one
        if(CurrentNode.hasNotBeenTraversed())
        {
            CodeAlgorithm(CurrentNode, CurrentNode.Son, CodeofSon); [2]
        }
        else
        {
            /* Father is recognized as a do-while structure, mark the Judgment
             * and link it with his Father, include do-while and nested do-while */
            if(Father is Judgment and Father.hasNotBeenRecognized()) [3]
            {
                Father.type = 'do-while';
                CurrentNode.doWhileCounter++; /*original value is zero*/
                Father.doWhileNode=CurrentNode;
                Father. doWhileCounter= CurrentNode.doWhileCounter;
                CurrentNode.doWhileRecodeCounter=CurrentNode.doWhileCounter;
            }
            if(CurrentNode.doWhileRecodeCounter>0) Recode(CurrentNode); [4]
        }
    }
    if(CurrentNode is Judgment) [5]
    {

        if(CurrentNode.hasNotBeenTraversed()) /*first in*/ [5-2]
        {
            /*Generate the code for CurrentNode;*/ [5-1]
            CurrentNode.code= CurrentCode
            /* push Judgment into StackofJudgement */ [6]
            Stack.push(StackofJudgement, Judgment) 

            /* j starts from 0 */
            for every son j of CurrentNode (except for convergence) do [7]
            {
                CurrentCode = CurrentCode+(j, 0)； /* nex layer */
                CodeAlgorithm(CurrentNode, CurrentNode.Son, CurrentCode); [7-1]
            }
            if CurrentNode has a son of convergence( as convergenceSon ) [7-2]
            {
                CodeAlgorithm(CurrentNode, convergenceSon, null); [8]
            }
            /* loop structures have been recognized, the left is selections */
            if(CurrentNode.isNotRecognized())
            {
                /* according to the condition of judgment, the detailed 
                structures of if-else/if/case can be recognized also.*/
                CurrentNode.type = 'selection';
            }
            /*Continue to process the nodes behind Convergence. */
            CurrentNode=CurrentNode.directJudgmentNode;
            CodeofSon = Increase y of CurrentCode by one
            CodeAlgorithm(CurrentNode, CurrentNode.Son, CodeofSon); [9]
        }
        else /* been traversed */
        {
            if(CurrentNode.isNotRecognized()) /* the first round trip */ [10]
            {
                /* recognized as while or for structures; */
                CurrentNode.type = 'while';
            }
            else /* been traversed and recognized */
            {
                if(Father is Judgment and Father.hasNotBeenRecognized()) [11]
                {
                    Father.type = 'do-while';
                    CurrentNode.doWhileCounter++; /* original value is zero */
                    Father.doWhileNode=CurrentNode;
                    Father.doWhileCounter= CurrentNode.doWhileCounter;
                    /*used for recode*/
                    CurrentNode.doWhileRecodeCounter=CurrentNode.doWhileCounter;
                }
                if(CurrentNode.doWhileRecodeCounter>0) {
                    Recode(CurrentNode,null); [12]
                }
            }
        }
    }
    if(CurrentNode is Convergence)
    {
        /* match a judgment node and a convergence node */ [13]
        if(CurrentNode.hasNotBeenTraversed())
        {
            /* as a judgment and a convergence is exist geminate, so the top
            judgment in stack must be able to match the current convergence */
            tempJudgeNode=Stack.Pop(StackofJudgement);
            CurrentNode.directJudgmentNode= tempJudgeNode;
            tempJudgeNode.directJudgmentConvergence= CurrentNode;
            CurrentNode.code= tempJudgeNode.code;
        } else return; [14]
    }
    if (CurrentNode is End) return; [15]
}
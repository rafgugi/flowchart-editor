/*************************************************************
Function: Convert the coded flowchart into PAD.
Input: Six hashtables, the coded flowchart.
Output: A PAD.
*************************************************************/
Define currentCode as string;
Node GetFromHashTable(currentCode) /*Get the node from hashtable by GetNode() method*/
Put the nodes(except for end and convergnece nodes) into HashTable according to their code length;
Block beginBlock;
beginBlock.NodeType=Begin;
ConvertToPAD(0001, beginBlock); /*the code of Begin is 0000, so original code is 0001*/
ConvertToPAD(currentCode, Blcok fatherBlock)
{
	CurrentNode= GetFromHashTable(currentCode);
	If (CurrentNode is null)) return; /* recursion return condition*/
	Block newBlock;
	If (CurrentNode is Process)
	{
		currentCode←currentCode.YY+1; /*the sequence part of code increase one*/
		newBlock.NodeType=Process;
		newBlock.codeblock=currentNode.Codeblock;
		newBlock.SubNodeList= null;
		fatherBlock. SubNodeList.append(newBlock);
		ConvertToPAD(currentCode, fatherBlock); /*as in the same layer, so the fatherBlock is the same*/
	}
	Else If (CurrentNode is Judgment)
	{
		If(CurrentNode.type is selection)
		{
			i←the sum of sons of CurrentNode;
			newBlock.NodeType= selectionType; /*here selectionType represents the types of selection*/
			newBlock.codeblock=currentNode.Codeblock;
			fatherBlock.SubNodeList.append(newBlock);
			for(j=0;j<i;j++)
			{
				Block selectionHeadPtr; /*every branch has its owe one*/
				selectionHeadPtr.NodeType=HeadPtr;
				newBlock.SubNodeList.append(selectionHeadPtr);
				TempCode =currentCode+0j00； /*nex layer*/
			ConvertToPAD(currentCode, selectionHeadPtr); /*begin a new layer, so the fatherBlock is
			newBlock */
			}
		/*as all the sub-nodes have been coded, so process the nodes at the same level */
		currentCode←currentCode.YY+1;
		ConvertToPAD(currentCode, fatherBlock);
		}
		If(CurrentNode.type is loop)
		{
			currentCode =currentCode+0000；/*into next layer*/
			newBlock.NodeType= loopType; /*here loopType represents the types of selection*/
			newBlock.codeblock=currentNode.Codeblock;
			ConvertToPAD(currentCode, newBlock); /*begin a new layer, so the fatherBlock is newBlock */
			/*as all the sub-nodes have been coded, so process the nodes at the same level */
			currentCode←currentCode.YY+1;
			ConvertToPAD(currentCode, fatherBlock);
		}
	}
}
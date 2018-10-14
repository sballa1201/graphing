prologues:=3;
outputformat:="mps";
outputtemplate:=("%j" & "." & outputformat);
verbatimtex
%&latex
\documentclass{minimal}
\begin{document}
etex
input metauml;
beginfig(0);
Class.A("Tree")
	("+Tree(String value)",
	"+String getValue()",	
	"+void setValue(String value)",
	"+List traverse()",
	"-List traverseHelper(Tree tree, &List order)")
	("+Tree right = null",
	"+Tree left = null",
	"-String value",
	);
    drawObject(A);
draw A.sw+(13,37.5) -- A.se+(-4,37.5);
endfig;
end
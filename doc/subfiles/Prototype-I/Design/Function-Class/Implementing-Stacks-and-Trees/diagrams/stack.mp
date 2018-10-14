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
Class.A("Stack")
	("+Stack(int maxSize)",
	"+double pop()",
	"+void push(double n)",
	"+int getHeight()")
	("-double[] stack",
    	"-int maxSize",
   	"-int height = 0",
    	"-int pointer = -1");
    	drawObject(A);
endfig;
end
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
Class.A("Function")
	("+Function(String expression)",
	"+Double evaluateStack(Double x)",
	"+Stack substitute(double x)",
        "-Tree createTree(String expression)",
        "-String checkBracket()",
        "-String standardize(String expression)")
	("-String expression",
        "-Stack postFixStack",
        "-Tree tree");
    	drawObject(A);
draw A.sw+(13,37.5) -- A.se+(-4,37.5);
draw A.sw+(13,48) -- A.se+(-65,48);
draw A.sw+(13,58.5) -- A.se+(-14,58.5);
endfig;
end
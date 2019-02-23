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
Class.A("ExpressionBox")
	("+ExpressionBox()",
	"-void changeLayer()",
	"-void remove()",
        "+void setID(int ID)",
        "+int getID()",
"+void setInputPane(InputPane inputPane)",
"+void set(Color color)")
("-int ID",
"-Color color",
"-InputPane inputPane",
"-boolean functionVisibility");
drawObject(A);
endfig;
end
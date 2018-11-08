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
Class.A("Layer")
("+Layer()",
"+draw()",
"convertX()",
"convertY()",
"clearCanvas()")
("Canvas canvas",
"GraphicsContext gc",
"int steps",
"double minX",
"double maxX",
"double minY",
"double maxY",
"double pixelWorthX",
"double pixelWorthY");

Class.B("ExplicitFunctionLayer")
("+ExplicitFunctionLayer(String expression)",
"+draw()")("-ExplicitFunction f");

Class.C("AxesLayer")
("+AxesLayer()",
"+draw()")();

B.ne = A.sw + (40, -40);
C.nw = A.se + (20, -40);


drawObjects(A,B,C);
link(inheritance)(pathStepY(B.n, A.s, 10));
link(inheritance)(pathStepY(C.n, A.s, 10));
endfig;
end
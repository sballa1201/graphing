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
Class.A("PlotPane")
("+PlotPane()",
"-void drawAll()",
"-void addLayer(Layer l)",
"+InputLayer getInputLayer()",
"+BooleanProperty getChangeViewport()")
("-List layers",
"-InputLayer inputLayer",
"-AxesLayer axesLayer",
"-BooleanProperty changeViewport");
Class.B("Pane")
()
();

A.n = B.s + (0,-30);

drawObject(A);
drawObject(B);


link(inheritance)( A.n -- B.s );
endfig;
end
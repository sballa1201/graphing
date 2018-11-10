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
"-void updatePixelWorth()",
"+DoubleProperty getMinX()",
"+DoubleProperty getMaxX()",
"+DoubleProperty getMinY()",
"+DoubleProperty getMaxY()",
"+DoubleProperty getPixelWorthX()",
"+DoubleProperty getPixelWorthY()",
"+IntegerProperty getSteps()")
("-List layers",
"-AxesLayer axes",
"-DoubleProperty minX",
"-DoubleProperty maxX",
"-DoubleProperty minY",
"-DoubleProperty maxY",
"-DoubleProperty pixelWorthX",
"-DoubleProperty pixelWorthY",
"-IntegerProperty steps");
Class.B("Pane")
()
();

A.n = B.s + (0,-30);

drawObject(A);
drawObject(B);


link(inheritance)( A.n -- B.s );
endfig;
end
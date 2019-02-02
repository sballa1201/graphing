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
"+void draw()",
"double convertX(double x)",
"double convertY(double y)",
"+Canvas getCanvas()",
"void clearCanvas()",
"void bindProperties(PlotPane plotPane)")
("Canvas canvas",
"GraphicsContext gc",
"IntegerProperty steps",
"DoubleProperty minX",
"DoubleProperty maxX",
"DoubleProperty minY",
"DoubleProperty maxY",
"DoubleProperty pixelWorthX",
"DoubleProperty pixelWorthY");

Class.B("ExplicitFunctionLayer")
("+ExplicitFunctionLayer(String expression)",
"+void draw()")("-ExplicitFunction f");

Class.C("AxesLayer")
("+AxesLayer()",
"+void draw()")();

Class.D("InputLayer")
("+InputLayer()",
"+void draw()",
"-void updatePixelWorth()",
"+DoubleProperty getMinX()",
"+DoubleProperty getMaxX()",
"+DoubleProperty getMinY()",
"+DoubleProperty getMaxY()",
"+DoubleProperty getPixelWorthX()",
"+DoubleProperty getPixelWorthY()",
"+IntegerProperty getSteps()",
"+BooleanProperty getChangeViewport()")
("-BooleanProperty changeViewport");

B.ne = A.sw + (-20, -40);
C.nw = A.se + (20, -40);
D.n = A.s + (0,-40);


drawObjects(A,B,C,D);
link(inheritance)(pathStepY(B.n, A.s, 10));
link(inheritance)(pathStepY(C.n, A.s, 10));
link(inheritance)(pathStepY(D.n, A.s, 10));
endfig;
end
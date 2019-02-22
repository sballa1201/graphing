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
Class.A("ShareLayers")
	("+ShareLayers()",
	"+void putLayer(int ID, Layer layer)",
	"+void removeLayer(int ID)",
        "-List getLayers()",
        "-BooleanProperty getChangeLayers()")
	("-Dictionary layers",
"-BooleanProperty changeLayers");
drawObject(A);

endfig;
end
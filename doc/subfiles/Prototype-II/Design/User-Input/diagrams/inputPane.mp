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
Class.A("InputPane")
	("+InputPane()",
	"-void addExpressionBox()",
	"+void removeExpressionBox(int ID)",
        "+void putLayer(int ID, Layer layer)",
        "+void removeLayer(int ID)",
"-void setupNewButton()",
"+setShareLayerStore(ShareLayers shareLayers)")
	("-VBox expressionBoxStore",
"-ShareLayers shareLayerStore",
"-int nextID",
"-Color[] colors");
drawObject(A);
endfig;
end
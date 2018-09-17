input metauml;
beginfig(1);
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
	draw A.sw+(13,4.5) -- A.se+(-4,4.5);
endfig;
end
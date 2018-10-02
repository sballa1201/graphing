prologues:=3;
outputformat:="mps";
outputtemplate:=("%j" & "." & outputformat);
verbatimtex
%&latex
\documentclass{minimal}
\begin{document}
etex
beginfig(0);
h = 200;
w = h*1.4;
z1 = (0,0);
z2 = (w,0);
z3 = (w,h);
z4 = (0,h);

z5 = ((2*w/7),0);
z6 = ((2*w/7),h-20);

draw z1--z2--z3--z4--z1;
draw z5--z6;
draw (0,h-20)--(w,h-20);

label("Input Equations",((w/7),(9*h/10)-10));
label(btex $y=x^2$ etex,((w/7),(8*h/10)-10));
label("New Equation",((w/7),(7*h/10)-10));

label("Plot",(4.5*w/7,(h/2)-10));

label("Main Ribbon",(w/2,h-10));
endfig;
end
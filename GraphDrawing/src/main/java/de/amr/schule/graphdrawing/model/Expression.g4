grammar Expression;

expression:
		expression '^' integer
	| 	variable
	|	floatNumber
	| 	integer
	;

variable: 'x';

floatNumber: DIGITS '.' DIGITS;

integer: DIGITS;

DIGITS : [0-9]+ ;
WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines

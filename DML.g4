grammar DML;

program					: (command ';' | query ';')* EOF;

IDENTIFIER				: ALPHA (ALPHA | DIGIT)*;
fragment ALPHA			: [a-zA-Z_];
fragment DIGIT			: [0-9];
INTEGER					: DIGIT+;
QUOTED					: '"' (ALPHA | INTEGER | [\t\-_ ])* '"';
string					: QUOTED | INTEGER;

// QUERIES
query					: relationName '<-' expression;
relationName			: IDENTIFIER;

expression				: selection
						| projection
						| rename
						| union
						| difference
						| product
						| naturalJoin
						| atomicExpression;

atomicExpression		: relationName | '(' expression ')';

selection				: 'select' '(' condition ')' atomicExpression;
condition				: conjunction ('||' conjunction)*;
conjunction				: comparison ('&&' comparison)*;
comparison				: operand operator operand | '(' condition ')';
operator				: '==' | '!=' | '<' | '>' | '<=' | '>=';

operand					: attributeName | string;

attributeName			: IDENTIFIER;
projection				: 'project' '(' attributeList ')' atomicExpression;
attributeList			: attributeName (',' attributeName)*;
rename					: 'rename' '(' attributeList ')' atomicExpression;
union					: atomicExpression '+' atomicExpression;
difference				: atomicExpression '-' atomicExpression;
product					: atomicExpression '*' atomicExpression;
naturalJoin				: atomicExpression '&' atomicExpression;

// commands

command					: open
						| close
						| write
						| exit
						| show
						| create
						| update
						| insert
						| delete;

open					: 'OPEN' relationName;
close					: 'CLOSE' relationName;
write					: 'WRITE' relationName;
exit					: 'EXIT';
show					: 'SHOW' relationName;
create					: 'CREATE' 'TABLE' relationName '(' typedAttributeList ')' 'PRIMARY' 'KEY' '(' attributeList ')';
update					: 'UPDATE' relationName 'SET' attributeName '=' string ( ',' attributeName '=' string)* 'WHERE' condition;
insert					: 'INSERT' 'INTO' relationName 'VALUES' 'FROM' ( '(' string (',' string)* ')' | 'RELATION' expression);

delete					: 'DELETE' 'FROM' relationName 'WHERE' condition;
typedAttributeList		: attributeName type (',' attributeName type)*;
type					: 'VARCHAR' '(' INTEGER ')' | 'INTEGER';

WS						: [ \t\n]+ -> skip;
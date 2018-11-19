package lexico;
import java_cup.runtime.Symbol;
import sintactico.*;

%%
%class Lexer
%ignorecase
%cup
%line
%column
%{
public String lexeme;
%}

// LENGUAGE: ABC
LETRA = [a-zA-Z_]
DIGITO = [0-9]
ESPACIO = [ \t \r \n \f \r\n]
SIMBOLO = [#!$%&?¡_]
// con [ ] funciona mejor, pero son parte de los símbolos de operadores xD
// OPERADOR = "*"|"+"|"-"|"/"|"="|","|"."|";"|":"|"<"|">"|"("|")"|"["|"]"
OPERADOR = ","|";"|"++"|"--"|">="|">"|"<="|"<"|"<>"|"="|"+"|"-"|"*"|"/"|"("|")"|"["|"]"|":="|"."|":"|"+="|"-="|"*="|"/="|">>"|"<<"|"<<="|">>="
ACENTO = [ñÑáéíóúÁÉÍÓÚ]
%%

{ESPACIO} {/*No se procesa*/} // espacio en blanco
"//".* {/*No se procesa*/} // dos slash de comentario
("\(\*" ~"\*\)" | "\(\*" "\*"+ "\)") {/*No se procesa*/} // comentario multilínea
("{" ~"}" | "{" "}") {/*No se procesa*/} // comentario multilínea
"<<EOF>>" {return new Symbol(sym.OPERADOR, yyline, yycolumn, yytext());}
"," {return new Symbol(sym.OPERADOR_COMA, yyline, yycolumn, yytext());}
";" {return new Symbol(sym.OPERADOR_PUNTO_Y_COMA, yyline, yycolumn, yytext());}
"++" {return new Symbol(sym.OPERADOR_INCREMENTO, yyline, yycolumn, yytext());}
"--" {return new Symbol(sym.OPERADOR_DISMINUCION, yyline, yycolumn, yytext());}
">=" {return new Symbol(sym.OPERADOR_MAYOR_IGUAL_QUE, yyline, yycolumn, yytext());}
">" {return new Symbol(sym.OPERADOR_MAYOR_QUE, yyline, yycolumn, yytext());}
"<=" {return new Symbol(sym.OPERADOR_MENOR_IGUAL_QUE, yyline, yycolumn, yytext());}
"<" {return new Symbol(sym.OPERADOR_MENOR_QUE, yyline, yycolumn, yytext());}
"<>" {return new Symbol(sym.OPERADOR_DIFERENTE_DE, yyline, yycolumn, yytext());}
"=" {return new Symbol(sym.OPERADOR_ASIGNACION_2, yyline, yycolumn, yytext());}
"+" {return new Symbol(sym.OPERADOR_ADICION, yyline, yycolumn, yytext());}
"-" {return new Symbol(sym.OPERADOR_SUSTRACCION, yyline, yycolumn, yytext());}
"*" {return new Symbol(sym.OPERADOR_MULTIPLICACION, yyline, yycolumn, yytext());}
"/" {return new Symbol(sym.OPERADOR_DIVISION, yyline, yycolumn, yytext());}
"(" {return new Symbol(sym.OPERADOR_PARENTESIS_ABRIR, yyline, yycolumn, yytext());}
")" {return new Symbol(sym.OPERADOR_PARENTESIS_CERRAR, yyline, yycolumn, yytext());}
"[" {return new Symbol(sym.OPERADOR_CORCHETE_ABRIR, yyline, yycolumn, yytext());}
"]" {return new Symbol(sym.OPERADOR_CORCHETE_CERRAR, yyline, yycolumn, yytext());}
":=" {return new Symbol(sym.OPERADOR_ASIGNACION_1, yyline, yycolumn, yytext());}
"." {return new Symbol(sym.OPERADOR, yyline, yycolumn, yytext());}
":" {return new Symbol(sym.OPERADOR_DOS_PUNTOS, yyline, yycolumn, yytext());}
"+=" {return new Symbol(sym.OPERADOR_ASIGNACION_ADICION, yyline, yycolumn, yytext());}
"-=" {return new Symbol(sym.OPERADOR_ASIGNACION_SUSTRACCION, yyline, yycolumn, yytext());}
"*=" {return new Symbol(sym.OPERADOR_ASIGNACION_MULTIPLICACION, yyline, yycolumn, yytext());}
"/=" {return new Symbol(sym.OPERADOR_ASIGNACION_DIVISION, yyline, yycolumn, yytext());}
">>" {return new Symbol(sym.OPERADOR_DESPLAZAMIENTO_DERECHA, yyline, yycolumn, yytext());}
"<<" {return new Symbol(sym.OPERADOR_DESPLAZAMIENTO_IZQUIERDA, yyline, yycolumn, yytext());}
"<<=" {return new Symbol(sym.OPERADOR_ASIGNACION_DESPLAZAMIENTO_DERECHA, yyline, yycolumn, yytext());}
">>=" {return new Symbol(sym.OPERADOR_ASIGNACION_DESPLAZAMIENTO_IZQUIERDA, yyline, yycolumn, yytext());}

"AND" {return new Symbol(sym.AND, yyline, yycolumn, yytext());}
"ARRAY" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"BEGIN" {return new Symbol(sym.BEGIN, yyline, yycolumn, yytext());}
"BOOLEAN" {return new Symbol(sym.BOOLEAN, yyline, yycolumn, yytext());}
"BYTE" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"CASE" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"CHAR" {return new Symbol(sym.CHAR, yyline, yycolumn, yytext());}
"CONST" {return new Symbol(sym.CONST, yyline, yycolumn, yytext());}
"DIV" {return new Symbol(sym.DIV, yyline, yycolumn, yytext());} // operador
"DO" {return new Symbol(sym.DO, yyline, yycolumn, yytext());}
"DOWNTO" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"ELSE" {return new Symbol(sym.ELSE, yyline, yycolumn, yytext());}
"END" {return new Symbol(sym.END, yyline, yycolumn, yytext());}
"FALSE" {return new Symbol(sym.FALSE, yyline, yycolumn, yytext());}
"FILE" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"FOR" {return new Symbol(sym.FOR, yyline, yycolumn, yytext());}
"FORWARD" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"FUNCTION" {return new Symbol(sym.FUNCTION, yyline, yycolumn, yytext());}
"GOTO" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"IF" {return new Symbol(sym.IF, yyline, yycolumn, yytext());}
"IN" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"INLINE" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"INT" {return new Symbol(sym.INT, yyline, yycolumn, yytext());}
"LABEL" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"LONGINT" {return new Symbol(sym.LONGINT, yyline, yycolumn, yytext());}
"MOD" {return new Symbol(sym.MOD, yyline, yycolumn, yytext());} // operador
"NIL" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"NOT" {return new Symbol(sym.NOT, yyline, yycolumn, yytext());} // operador
"OF" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"OR" {return new Symbol(sym.OR, yyline, yycolumn, yytext());} // operador
"PACKED" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"PROCEDURE" {return new Symbol(sym.PROCEDURE, yyline, yycolumn, yytext());}
"PROGRAM" {return new Symbol(sym.PROGRAM, yyline, yycolumn, yytext());}
"READ" {return new Symbol(sym.READ, yyline, yycolumn, yytext());}
"REAL" {return new Symbol(sym.REAL, yyline, yycolumn, yytext());}
"RECORD" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"REPEAT" {return new Symbol(sym.REPEAT, yyline, yycolumn, yytext());}
"SET" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"SHORTINT" {return new Symbol(sym.SHORTINT, yyline, yycolumn, yytext());}
"STRING" {return new Symbol(sym.STRING, yyline, yycolumn, yytext());}
"THEN" {return new Symbol(sym.THEN, yyline, yycolumn, yytext());}
"TO" {return new Symbol(sym.TO, yyline, yycolumn, yytext());}
"TRUE" {return new Symbol(sym.TRUE, yyline, yycolumn, yytext());}
"TYPE" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"UNTIL" {return new Symbol(sym.UNTIL, yyline, yycolumn, yytext());}
"VAR" {return new Symbol(sym.VAR, yyline, yycolumn, yytext());}
"WHILE" {return new Symbol(sym.WHILE, yyline, yycolumn, yytext());}
"WITH" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());}
"WRITE" {return new Symbol(sym.WRITE, yyline, yycolumn, yytext());}
"XOR" {return new Symbol(sym.PALABRA_RESERVADA, yyline, yycolumn, yytext());} // operador

// |-------------------- RECONOCER EXPRESIONES --------------------| // {return new Symbol(sym.OPERADOR, yyline, yychar, yytext());}
// Identificadores
{LETRA}(({LETRA}|{DIGITO}){0, 126})? {return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext());}

// Flotantes
(({DIGITO}+"."{DIGITO}+)) |
    (({DIGITO}"."{DIGITO}+)([eE][-]?{DIGITO}+)) {return new Symbol(sym.LITERAL_NUM_FLOTANTE, yyline, yycolumn, yytext());}

// Literales
((\"[^\"] ~\")|(\"\")) {return new Symbol(sym.LITERAL_STRING, yyline, yycolumn, yytext());}
//\"({LETRA}|{DIGITO}|{ESPACIO}|{SIMBOLO})*+\" | ("#"{DIGITO}{DIGITO}) {lexeme=yytext(); line=yyline; return LITERAL_STRING;}
("#"{DIGITO}+) {return new Symbol(sym.LITERAL_STRING, yyline, yycolumn, yytext());}
("(-"{DIGITO}+")")|{DIGITO}+ {return new Symbol(sym.LITERAL_NUM_ENTERO, yyline, yycolumn, yytext());} // Un numero entero


// |-------------------- RECONOCER ERRORES --------------------| //
// Identificadores
//identificador mayor a 127 caracteres
{LETRA}(({LETRA}|{DIGITO}){127})({LETRA}|{DIGITO})* {return new Symbol(sym.ERROR_IDENTIFICADOR, yyline, yycolumn, yytext());}
//identificador no comienza con digito
(({DIGITO}+)({LETRA}|{ACENTO}))(({LETRA}|{DIGITO}|{SIMBOLO}|{ACENTO}))* {return new Symbol(sym.ERROR_IDENTIFICADOR, yyline, yycolumn, yytext());}
//identificador no lleva simbolos
({LETRA}|{ACENTO}|{SIMBOLO})(({LETRA}|{DIGITO}|{SIMBOLO}|{ACENTO}))+ {return new Symbol(sym.ERROR_IDENTIFICADOR, yyline, yycolumn, yytext());}

// Flotantes
// 12.12.12...
{DIGITO}+"."{DIGITO}+("."{DIGITO}*)+ {return new Symbol(sym.ERROR_LITERAL, yyline, yycolumn, yytext());}
// .12e12 / .12e / .12  | 12.23e-23.12
("."{DIGITO}+([eE][-]?{DIGITO}*)?) | ({DIGITO}+"."{DIGITO}+([eE][-]?)({DIGITO}*"."{DIGITO}*))* {return new Symbol(sym.ERROR_LITERAL, yyline, yycolumn, yytext());}
// 12ab.12 | ab12.12
({DIGITO}+{LETRA}+"."{DIGITO}+) | ({LETRA}+{DIGITO}+"."{DIGITO}+) {return new Symbol(sym.ERROR_LITERAL, yyline, yycolumn, yytext());}
// 12.12ab | 12.ab12
({DIGITO}+"."{DIGITO}+{LETRA}+) | ({DIGITO}+"."{LETRA}+{DIGITO}+) {return new Symbol(sym.ERROR_LITERAL, yyline, yycolumn, yytext());}
// ab.12ab | ab.ab12
({LETRA}+"."{DIGITO}+{LETRA}+) | ({LETRA}+"."{LETRA}+{DIGITO}+) {return new Symbol(sym.ERROR_LITERAL, yyline, yycolumn, yytext());}
// 12. | 12e.
({DIGITO}+{LETRA}*".") {return new Symbol(sym.ERROR_LITERAL, yyline, yycolumn, yytext());}
// 3,14
{DIGITO}+","{DIGITO}+ {return new Symbol(sym.ERROR_LITERAL, yyline, yycolumn, yytext());}

// Literales
"#"{LETRA}+ {return new Symbol(sym.ERROR_LITERAL, yyline, yycolumn, yytext());}
'[^'] ~' {return new Symbol(sym.ERROR_LITERAL, yyline, yycolumn, yytext());}

// Comentarios
\"[^\"]* {return new Symbol(sym.error, yyline, yycolumn, yytext());}
\(\*[^\)\*]* {return new Symbol(sym.error, yyline, yycolumn, yytext());}
\{[^\}]* {return new Symbol(sym.error, yyline, yycolumn, yytext());}

. {return new Symbol(sym.error, yyline, yycolumn, yytext());}

# Compilador ABC

Compilador del lenguaje ABC, hecho en Java utilizando JFlex y CUP.

Incluye 3 fases: Analizador léxico (scanner), sintáctico (parser) y semántico. 


## Inicio

A continuación, se detallan aspectos generales del compilador.

### Prerequisitos

* [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows) - El ambiente de desarrollo del proyecto.
* [JFlex](http://www.jflex.de/download.html) - El generador de analizador léxico.
* [CUP](http://www2.cs.tum.edu/projects/cup/) - El generador de parser.

## Lenguaje ABC

Tokens, sintáxis y gramática del lenguaje elaborado.

### Operadores

```
"," | ";" | "++" | "--" | ">=" | ">" | "<=" | "<" | "<>" (diferente que) | "=" | "-" | "+" | "*" | "/" | "(" | ")" | ":=" | "." | ":" | "+=" | "*=" | "/=" | "NOT" | "OR" | "AND" | "XOR" | "DIV" | "MOD"
```

### Palabras Reservadas

```
AND | BEGIN | BOOLEAN | CHAR | CONST | DIV | DO | ELSE | END | FALSE | FUNCTION | IF | INT | LONGINT | MOD | NOT | OR | PROCEDURE | PROGRAM | READ | REAL | SHORTINT | STRING | THEN | TRUE | VAR | WHILE | WRITE | XOR	
```

La escritura de las palabras reservadas no importa, es decir, es equivalente escribir por ejemplo: *BEGIN*, *begin*, *Begin*, *BeGiN*, etc.

### Identificadores

Secuencia de 1 a 127 caracteres que inician con una letra, no tienen espacios ni simbolos (&, !, ?, etc) y no es una palabra reservada.

### Literales

Se permiten números enteros, números flotantes, caracteres y cadenas de texto (string).

- Los números reales llevan al menos un dígito de cada lado del punto decimal. Además, se permite la notación cientifica.

```
5.0 | 0.5 | 3.0E5 | 1.5E-4
```

- Las cadenas de texto y los caracteres se representan entre " ", donde estos pueden ser de una o varias líneas. Además, los caracteres se pueden representar con el signo # seguido de un número entero.

```
"Cadena de texto de una línea"

"
Cadena de texto
multilínea
"

#7 | #12 | #898
``` 

## Estructura del lenguaje

### Estructura del programa

```
PROGRAM nombreDelPrograma
    // Declaración de constantes globales           (opcional)
    // Declaración de variables globales            (opcional)
    // Declaración de funciones y/o procedimientos  (opcional)

BEGIN
    // Cuerpo    (opcional)
END
```

*Cuerpo*: Incluye estructuras de control, las funciones *READ* y *WRITE*, asignación de variables y/o constantes, y expresiones aritméticas.

### Declaración de variables y constantes

* **Variables**:

```
VAR
nombre, apellido: STRING;
contador: INT;
```

* **Constantes**:

```
CONST
Pi = 3.1415926;
guitarra = "ESP";
```

### Funciones y Procedimientos

* **Funciones**:

```
FUNCTION miFuncion(int num): int
// Declaración de constantes locales    (opcional)
// Declaración de variables locales     (opcional)
BEGIN
    // Cuerpo    (opcional)
    miFuncion := num;
END
```

Las funciones pueden contener o no parametros de entrada y es obligatorio que retornen un valor. Para el retorno de las funciones se debe establecer en la última línea del cuerpo, el nombre de la función seguido del operacidor de asignación y el valor de retorno.

* **Procedimientos**:

```
PROCEDURE miProcedimiento(string pCadena)
// Declaración de constantes locales    (opcional)
// Declaración de variables locales     (opcional)
BEGIN
    // Cuerpo    (opcional)
END
```

*Cuerpo*: Incluye estructuras de control, las funciones *READ* y *WRITE*, asignación de variables y/o constantes, y expresiones aritméticas.

### Estructuras de control

* **IF**:

```
IF (var1 < var2) THEN
    // Cuerpo    (opcional)
ELSE
    // Cuerpo    (opcional)
END
```

*Condición*: Las condiciones pueden ser de 1 a N condiciones y pueden estar unidas por *AND* u *OR*. La estructura *ELSE* es opcional así como el cuerpo.

*Cuerpo*: Incluye estructuras de control, las funciones *READ* y *WRITE*, asignación de variables y/o constantes, y expresiones aritméticas.

* **While**:

```
WHILE (var1 <> var2) DO
BEGIN
    // Cuerpo     (opcional)
END
```

*Condición*: Las condiciones pueden ser de 1 a N condiciones y pueden estar unidas por *AND* u *OR*. La estructura *ELSE* es opcional así como el cuerpo.

*Cuerpo*: Incluye estructuras de control, las funciones *READ* y *WRITE*, asignación de variables y/o constantes, y expresiones aritméticas.


* **FOR**:

```
FOR var1 := var2 TO var3 DO
BEGIN
    // Cuerpo    (opcional)
END
```

*Cuerpo*: Incluye estructuras de control, las funciones *READ* y *WRITE*, asignación de variables y/o constantes, y expresiones aritméticas.

### Asignación de variables

```
identificador := valor;
```

### Función Read y Write

```
read(); 
read(var1); 
write(var1); 
write(var1, ..., varN);
```

### Expresiones aritméticas

Algunos ejemplos:

```
var1 := 12 * (miFuncion(12, var1) - 90);
var2 := 12 DIV 30;
var3 := 30 / 12;
var4++;
++var4;
var4--;
--var4;
var5 := var4++;
var5 := --var4;
var6 += miFuncion() * 12;
```

### Comentarios

Se cuenta con 2 tipos de comentarios:
- Comentarios de línea:

```
// Este es un comentario de línea
```

- Comentarios de bloque (2 formas):

```
{
    Primer tipo de comentario de bloque
}
```

```
(*
    Segundo tipo de comentario d bloque
*)
```

## Interfaz de Usuario

Se cuenta con un TextArea donde el usuario es posible ingresar el código respectivo a analizar. Además, existe un apartado con una serie de *tabs* donde es posible observar los tokens encontrados, los errores léxicos, los errores sintáctivos, los errores semánticos, la tabla de símbolos y finalmente, la traducción del lenguaje ABC a lenguaje ensamblador.

**Token encontrados**:

Se detalla el token, el tipo y la linea.

**Errores**

Se especifica el token o identificador que ocasiona el error, donde se incluye la línea y columna, y el segmento del código donde se encuentra dicho error.

**Tabla de símbolos**

Muestra el tipo de identificador o token, además del nombre dentro del código.
En lo que respecta funciones y procedimientos, se detalla su tipo (si es función o procedimiento) el tipo de retorno (si es procedimiento es *null*), y la cantidad de parametros que recibe, especificando la respectiva información de dichos parametros de entrada; el nombre y su tipo de dato.

**Traducción**

Se muestra la traducción de estructuras de control *IF* y *WHILE*, declaraciones de variables globales y espresiones binarias aritméticas.

## Autores

* **José Navarro** - [JoseNA12](https://github.com/JoseNA12)
* **Greivin Berrocal** - [berrocal9470](https://github.com/berrocal9470)

* *Instituto Tecnológico de Costa Rica.*

* *27/11/2018.*

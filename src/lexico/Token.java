package lexico;

public enum Token {

    // |------------------- IDENTIFICADORES -------------------|
    IDENTIFICADOR("Identificador"),

    // |------------------- OPERADORES -------------------|
    OPERADOR("Operador"),
    // Aritmeticos : XOR DIV MOD
    OPERADOR_ADICION("Operador de adición"),
    OPERADOR_SUSTRACCION("Operador de sustracción"),
    OPERADOR_MULTIPLICACION("Operador de multiplicación"),
    OPERADOR_DIVISION("Operador de división"),
    OPERADOR_SIGNO("Operador de signo"),

    // Monarios
    OPERADOR_INCREMENTO("Operador de increment unario"),
    OPERADOR_DISMINUCION("Operador de disminución unario"),

    // Lógicos : NOT OR AND
    OPERADOR_MAYOR_QUE("Operador mayor que"),
    OPERADOR_MAYOR_IGUAL_QUE("Operador mayor o igual que"),
    OPERADOR_MENOR_QUE("Operador menor que"),
    OPERADOR_MENOR_IGUAL_QUE("Operador menor o igual que"),
    OPERADOR_IGUAL("Operador de igualdad"),
    OPERADOR_DISTINTO("Operador distinto que"),

    // Asignación
    OPERADOR_ASIGNACION("Operador de asignación simple"),
    OPERADOR_ASIGNACION_MULTIPLICACION("Operador de asignación de multiplicacion"),
    OPERADOR_ASIGNACION_DIVISION("Operador de asignación de división"),
    OPERADOR_ASIGNACION_ADICION("Operador de asignación de adición"),
    OPERADOR_ASIGNACION_SUSTRACCION("Operador de asignación de sustracción"),
    OPERADOR_ASIGNACION_DESPLAZAMIENTO_DERECHA("Operador de asignación del desplazamiento derecha"),
    OPERADOR_ASIGNACION_DESPLAZAMIENTO_IZQUIERDA("Operador de asignación del desplazamiento izquierda"),

    // Bits
    OPERADOR_DESPLAZAMIENTO_DERECHA("Operador desplazamiento derecha"),
    OPERADOR_DESPLAZAMIENTO_IZQUIERDA("Operador desplazamiento izquierda"),

    // Corchetes
    OPERADOR_PARENTESIS_ABRIR("Operador parentesis abierto"), // ( )
    OPERADOR_PARENTESIS_CERRAR("Operador parentesis cerrado"),
    OPERADOR_CORCHETE_ABRIR("Operador corchete abierto"), // [ ]
    OPERADOR_CORCHETE_CERRAR("Operador corchete cerrado"),
    OPERADOR_LLAVE_ABRIR("Operador llave abierta"), //  { }
    OPERADOR_LLAVE_CERRAR("Operador llave cerrada"),


    // |------------------- PALABRAS RESERVADA -------------------|
    PALABRA_RESERVADA("Palabra reservada"),

    // |------------------- LITERALES -------------------|
    LITERAL("Literal"),
    LITERAL_NUM_ENTERO("Literal número entero"),
    LITERAL_NUM_FLOTANTE("Literal número flotante"),
    LITERAL_CARACTER("Literal caracter"),
    LITERAL_STRING("Literal strings"),

    // |------------------- ERRRORES -------------------|
    ERROR("Error"),
    ERROR_LITERAL("Error literal"),
    ERROR_IDENTIFICADOR("Error de identificador"),
    ERROR_PALABRA_RESERVADA("Error de palabra reservada"),
    ERROR_OPERADOR("Error de operador"),

    COMENTARIO("Comentario");


    String nombre;
    Token(String pNombre) { nombre = pNombre; }

    public String getNombre() { return nombre; }
}

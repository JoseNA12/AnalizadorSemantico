package lexico;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lib.JavaKeywordsAsyncDemo;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.fxmisc.wellbehaved.event.EventPattern;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;
import org.reactfx.Subscription;
import vista.ItemTablaErrores;
import vista.ItemTablaTokens;
import vista.LineaToken;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java_cup.runtime.*;

import sintactico.*;

public class Main extends Application implements Cloneable  {

    public static Main miInstancia;
    @FXML public TextArea ta_insertar_texto_id;
    @FXML public TableView<ItemTablaTokens> tv_tokens_encontrados_id;
    @FXML public TableColumn<ItemTablaTokens, String> tc_token_id, tc_tipo_token_id, tc_linea_token_id;
    @FXML public TableView<ItemTablaErrores> tv_errores_lexicos_id;
    @FXML public TableColumn<ItemTablaErrores, String> tc_error_id, tc_linea_error_id, tc_tipo_error_id;
    private final ObservableList<ItemTablaTokens> info_tabla_tokens = FXCollections.observableArrayList();
    private final ObservableList<ItemTablaErrores> info_tabla_errores = FXCollections.observableArrayList();

    @FXML public Button btn_abrir_archivo_id, btn_procesar_id;

    @FXML private TextArea ta_errores_sintacticos_id;

    @FXML public CodeArea ca_insertar_texto_id;
    private ExecutorService executor;

    private List<LineaToken> tokenslist, tokenslistErrores;

    private Stage miPrimaryStage;

    private static final String[] KEYWORDS = arrayToLower(sym.terminalNames);



    @Override
    public void start(Stage primaryStage) throws Exception
    {
        miPrimaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../vista/GUI.fxml"));
        miPrimaryStage.setTitle("Analizador Léxico y Sintáctico");
        Scene miScene = new Scene(root);
        miScene.getStylesheets().add(Main.class.getResource("java-keywords.css").toExternalForm());
        miPrimaryStage.setScene(miScene);
        miPrimaryStage.show();
    }

    @Override
    public void stop() {
        executor.shutdown();
    }

    public static void main(String[] args)
    {
        Path pathActual = Paths.get("");
        String pathRaiz = pathActual.toAbsolutePath().toString();

        generarLexer(pathRaiz);
        generarSyntax(pathRaiz);

        launch(args);
    }

    public static void generarLexer(String pPathRaiz)
    {
        String path = pPathRaiz + "/src/lexico/Lexer.flex";

        File file = new File(path);
        jflex.Main.generate(file);
    }

    private static void generarSyntax(String pPathRaiz)
    {
        String path = pPathRaiz + "/src/sintactico/Syntax.cup";

        String[] asintactico = {"-parser", "Syntax",
                                "-destdir", pPathRaiz + "\\src\\sintactico\\",
                                "-symbols", "sym",
                                path};

        try { java_cup.Main.main(asintactico); }
        catch (Exception e) { e.printStackTrace(); }
    }

    public void probarLexerFile()
    {
        reestablecerComponentes();

        File fichero = new File ("fichero.txt");
        PrintWriter writer;

        try {
            writer = new PrintWriter(fichero);
            // writer.print(ta_insertar_texto_id.getText()); // .toUpperCase();
            writer.print(ca_insertar_texto_id.getText());
            writer.close();
        }
        catch (FileNotFoundException ex) {
            // Logger.getLogger(interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

        // 2 reader's para solucionar problemas con las referencias de los objetos, porque el lexico itera el lexeme y
        // jala tambien la referencia del reader y desmadra los objetos para cuando el sintactico realiza el analisis
        Reader readerLexico = null, readerSintactico = null;
        try {
            readerLexico = new BufferedReader(new FileReader("fichero.txt"));
            readerSintactico = new BufferedReader(new FileReader("fichero.txt"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // reader = new BufferedReader(new StringReader(ta_insertar_texto_id.getText()));

        ejecutarAnalizadorLexico(readerLexico, readerSintactico);
    }

    private void ejecutarAnalizadorLexico(Reader readerLexico, Reader readerSintactico)
    {
        Lexer lexerLexico = new Lexer(readerLexico);
        Lexer lexerSintactico = new Lexer(readerSintactico);

        while (true)
        {
            Symbol token = null;
            try
            {
                token = lexerLexico.next_token();
            }
            catch (IOException e) { }

            if (token == null || token.value == null)
            {
                agregarElementosTablaTokens();
                agregarElementosTablaTokensErrores();
                break; // return;
            }

            if ((token.sym != sym.error) && (token.sym != sym.ERROR_LITERAL) && (token.sym != sym.ERROR_IDENTIFICADOR
                    && (token.sym != sym.ERROR_OPERADOR) && (token.sym != sym.ERROR_PALABRA_RESERVADA)))
            {
                agregarLineaToken(token.value.toString(), sym.terminalNames[token.sym], token.left);
            }
            else
            {
                agregarLineaTokenErrores(token.value.toString(), sym.terminalNames[token.sym], token.left);
            }
        }
        ejecutarAnalizadorSintactico(lexerSintactico);
    }

    private void ejecutarAnalizadorSintactico(Lexer lexer)
    {
        Syntax syntax = new Syntax(lexer);

        try {
            syntax.parse();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize(){
        initTablesViews();
        initCodeArea();

        miInstancia = this;
    }

    private void initCodeArea()
    {
        executor = Executors.newSingleThreadExecutor();
        ca_insertar_texto_id.setParagraphGraphicFactory(LineNumberFactory.get(ca_insertar_texto_id));
        Subscription cleanupWhenDone = ca_insertar_texto_id.multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .supplyTask(this::computeHighlightingAsync)
                .awaitLatest(ca_insertar_texto_id.multiPlainChanges())
                .filterMap(t -> {
                    if(t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(this::applyHighlighting);

        // modificar los espacios del tab, porque los hace muy grandes
        InputMap<KeyEvent> im = InputMap.consume(
                EventPattern.keyPressed(KeyCode.TAB),
                e -> {
                    ca_insertar_texto_id.replaceSelection(miTab);
                    e.consume();
                });

        Nodes.addInputMap(ca_insertar_texto_id, im);
        // call when no longer need it: `cleanupWhenFinished.unsubscribe();`

        //ca_insertar_texto_id.replaceText(0, 0, sampleCode);
    }

    /**
     * Inicializar los valores de las tablas asi como sus columnas
     */
    private void initTablesViews()
    {
        // Tokens
        tc_token_id.setCellValueFactory(new PropertyValueFactory<>("token"));
        tc_tipo_token_id.setCellValueFactory(new PropertyValueFactory<>("tipoToken"));
        tc_linea_token_id.setCellValueFactory(new PropertyValueFactory<>("linea"));
        tv_tokens_encontrados_id.setItems(info_tabla_tokens);

        // Errores
        tc_error_id.setCellValueFactory(new PropertyValueFactory<>("error"));
        tc_tipo_error_id.setCellValueFactory(new PropertyValueFactory<>("tipoError"));
        tc_linea_error_id.setCellValueFactory(new PropertyValueFactory<>("linea_error"));
        tv_errores_lexicos_id.setItems(info_tabla_errores);
    }

    /**
     * Agrega una nueva linea al tokenlist
     * Si ya existe el token llama a agregarLinea(int), sino crea una nueva Linea de Token con un nuevo HashMap
     * @param token token analizado
     * @param tipoToken tipo del token analizado
     * @param numeroLinea número de línea de aparición del token analizado
     */
    private void agregarLineaToken(String token, String tipoToken, int numeroLinea){
        LineaToken linea = null;
        boolean existe = false;
        for(int i=0; i < tokenslist.size(); i++)
        {
            linea = tokenslist.get(i);
            if(linea.token.equalsIgnoreCase(token))
            {
                existe = true;
                break;
            }
        }
        if(existe){
            linea.agregarLinea(numeroLinea);
        }else
        {
            Map<Integer, Integer> lineasAparicion = new HashMap<Integer, Integer>();
            lineasAparicion.put(numeroLinea, 1);
            tokenslist.add(new LineaToken(token, tipoToken, lineasAparicion));
        }
    }

    /**
     * Encargado de agregar valores a la tabla de tokens de la interfaz
     * Utiliza el LinkedList tokenlist que posee todas las lineas de código resumidas por apariciones del token
     */
    private void agregarElementosTablaTokens()
    {
        String lineas;
        for(LineaToken l : tokenslist)
        {
            lineas = "";
            Set<Integer> clavesLineas = l.lineasAparicion.keySet();     //retorna el set de claves del map
            for (Iterator<Integer> it = clavesLineas.iterator(); it.hasNext();)
            {
                Integer key = it.next();
                int cantidadApariciones = l.lineasAparicion.get(key);

                if(cantidadApariciones > 1)
                {
                    lineas += (key + 1) + "(" + l.lineasAparicion.get(key) + "), ";
                }else
                    {
                    lineas += (key + 1) + ", ";
                }
            }
            info_tabla_tokens.add(new ItemTablaTokens(new SimpleStringProperty(l.token),
                    new SimpleStringProperty(l.tipoToken), new SimpleStringProperty(lineas)));
        }
    }

    /**
     * Agrega una nueva linea al tokenlistErrrores
     * Si ya existe el token llama a agregarLinea(int), sino crea una nueva Linea de Token con un nuevo HashMap
     * @param token token analizado
     * @param numeroLinea número de línea de aparición del token analizado
     */
    private void agregarLineaTokenErrores(String token, String tipoError, int numeroLinea){
        LineaToken linea = null;
        boolean existe = false;
        for(int i=0; i< tokenslistErrores.size(); i++){
            linea = tokenslistErrores.get(i);
            if(linea.token.equalsIgnoreCase(token)){
                existe = true;
                break;
            }
        }
        if(existe){
            linea.agregarLinea(numeroLinea);
        }else{
            Map<Integer, Integer> lineasAparicion = new HashMap<Integer, Integer>();
            lineasAparicion.put(numeroLinea, 1);
            tokenslistErrores.add(new LineaToken(token, tipoError, lineasAparicion));
        }
    }

    /**
     * Encargado de agregar valores a la tabla de tokens de errores de la interfaz
     * Utiliza el LinkedList tokenlist que posee todas las lineas de código resumidas por apariciones del token
     */
    private void agregarElementosTablaTokensErrores()
    {
        String lineas;
        for(LineaToken l : tokenslistErrores){
            lineas = "";
            Set<Integer> clavesLineas = l.lineasAparicion.keySet();     //retorna el set de claves del map
            for (Iterator<Integer> it = clavesLineas.iterator(); it.hasNext(); ) {
                Integer key = it.next();
                int cantidadApariciones = l.lineasAparicion.get(key);

                if(cantidadApariciones > 1){
                    lineas += (key + 1) + "(" + l.lineasAparicion.get(key) + "), ";
                }else{
                    lineas += (key + 1) + ", ";
                }
            }
            info_tabla_errores.add(new ItemTablaErrores(new SimpleStringProperty(l.token), new SimpleStringProperty(l.tipoToken), new SimpleStringProperty(lineas)));
        }
    }

    /**
     * Reestablece y limpia los valores asociados a los componentes graficos
     */
    private void reestablecerComponentes()
    {
        tokenslist = new LinkedList<LineaToken>();
        tokenslistErrores = new LinkedList<LineaToken>();
        info_tabla_tokens.clear();
        info_tabla_errores.clear();
        ta_errores_sintacticos_id.clear();
    }

    /**
     * Encargado de abrir el FileChooser para seleccionar el archivo txt
     */
    public void btn_action_abrirArchivo() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showOpenDialog(miPrimaryStage);
        //File file = new File("fichero.txt");
        if(file != null){
            // ta_insertar_texto_id.setText(readFile(file));
            ca_insertar_texto_id.replaceText(readFile(file));
        }
    }

    /**
     * Encargado de leer el archivo txt en memoria
     * @param file
     * @return
     */
    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text.replaceAll("\t", miTab) + "\n");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return stringBuffer.toString();
    }

    private void imprimir(String pMsg) { System.out.println(pMsg); }

    private void imprimir(int pMsg) { System.out.println(pMsg); }

    private static String[] arrayToLower(String[] pArray)
    {
        List<String> list = Arrays.asList(pArray);
        list.replaceAll(String::toLowerCase);

        return list.toArray(new String[list.size()]);
    }

    public void agregarErrorSintactico(String pError)
    {
        ta_errores_sintacticos_id.appendText("\n" + pError);
    }

    // ============================ CodeArea ============================ \\

    private String miTab = "    ";

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = ca_insertar_texto_id.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return computeHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
        ca_insertar_texto_id.setStyleSpans(0, highlighting);
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );
}

package vista;

import javafx.beans.property.SimpleStringProperty;

public class ItemTablaTokens {

    private final SimpleStringProperty token, tipoToken, linea;

    public ItemTablaTokens(SimpleStringProperty token, SimpleStringProperty tipoToken, SimpleStringProperty linea) {
        this.token = token;
        this.tipoToken = tipoToken;
        this.linea = linea;
    }

    public String getToken() {
        return token.get();
    }

    public SimpleStringProperty tokenProperty() {
        return token;
    }

    public void setToken(String token) {
        this.token.set(token);
    }

    public String getTipoToken() {
        return tipoToken.get();
    }

    public SimpleStringProperty tipoTokenProperty() {
        return tipoToken;
    }

    public void setTipoToken(String tipoToken) {
        this.tipoToken.set(tipoToken);
    }

    public String getLinea() {
        return linea.get();
    }

    public SimpleStringProperty lineaProperty() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea.set(linea);
    }
}

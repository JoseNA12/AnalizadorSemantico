package vista;

import javafx.beans.property.SimpleStringProperty;

public class ItemTablaErrores {

    private final SimpleStringProperty error, tipoError, linea_error;

    public ItemTablaErrores(SimpleStringProperty error, SimpleStringProperty tipoError, SimpleStringProperty linea_error) {
        this.error = error;
        this.tipoError = tipoError;
        this.linea_error = linea_error;
    }

    public String getError() {
        return error.get();
    }

    public SimpleStringProperty errorProperty() {
        return error;
    }

    public void setError(String error) {
        this.error.set(error);
    }

    public String getTipoError() {
        return tipoError.get();
    }

    public SimpleStringProperty tipoErrorProperty() {
        return tipoError;
    }

    public void setTipoError(String tipoError) {
        this.tipoError.set(tipoError);
    }

    public String getLinea_error() {
        return linea_error.get();
    }

    public SimpleStringProperty linea_errorProperty() {
        return linea_error;
    }

    public void setLinea_error(String linea_error) {
        this.linea_error.set(linea_error);
    }
}

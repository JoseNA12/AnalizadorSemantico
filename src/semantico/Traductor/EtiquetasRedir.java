package semantico.Traductor;/* Clase que agrupa dos etiquetas de inicio y fin */

public class EtiquetasRedir {

	private String etiqueta_Inicio;
	private String etiqueta_Final;

	public EtiquetasRedir(String etiqueta_Inicio, String etiqueta_Final) {
		this.etiqueta_Inicio = etiqueta_Inicio;
		this.etiqueta_Final = etiqueta_Final;
	}

	public String getEtiqueta_Inicio() {
		return etiqueta_Inicio;
	}

	public void setEtiqueta_Inicio(String etiqueta_Inicio) {
		this.etiqueta_Inicio = etiqueta_Inicio;
	}

	public String getEtiqueta_Final() {
		return etiqueta_Final;
	}

	public void setEtiqueta_Final(String etiqueta_Final) {
		this.etiqueta_Final = etiqueta_Final;
	}
}

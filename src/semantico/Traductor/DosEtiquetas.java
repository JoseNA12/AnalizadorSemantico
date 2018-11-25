package semantico.Traductor;/* Clase que agrupa dos etiquetas de condición */

public class DosEtiquetas {

	private String etiqueta_1;
	private String etiqueta_2;

	public DosEtiquetas(String etiqueta_1, String etiqueta_2) {
		this.etiqueta_1 = etiqueta_1;
		this.etiqueta_2 = etiqueta_2;
	}

	public String getEtiqueta_1() {
		return etiqueta_1;
	}

	public void setEtiqueta_1(String etiqueta_1) {
		this.etiqueta_1 = etiqueta_1;
	}

	public String getEtiqueta_2() {
		return etiqueta_2;
	}

	public void setEtiqueta_2(String etiqueta_2) {
		this.etiqueta_2 = etiqueta_2;
	}
}

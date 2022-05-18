package br.com.anhembi.iHealth.modelo;

public enum Status {
	
	ANALISE ("EM ANALISE"),
	ACEITO ("ACEITO"),
	RECUSADO ("RECUSADO"),
	ENTREGUE ("ENTREGUE");
	
	public String getName() {
		return name;
	}

	private final String name;
	
	Status(String name){
		this.name = name;
	}
}

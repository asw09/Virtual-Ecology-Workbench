package VEW.Planktonica2.model;

import VEW.Common.XML.XMLTag;

public class VarietyVariable extends Variety {

	public VarietyVariable(FunctionalGroup funcGroup) {
		super(funcGroup);
	}
	
	@Override
	public XMLTag buildToXML() {
		XMLTag varTag = super.buildToXML();
		varTag.setName("varietyvariable");
		return varTag;
	}

	
}

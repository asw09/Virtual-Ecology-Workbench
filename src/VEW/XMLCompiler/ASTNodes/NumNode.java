package VEW.XMLCompiler.ASTNodes;


public class NumNode extends ExprNode {

	private float value;
	
	public NumNode(float value) {
		this.value = value;
	}
	
	@Override
	public void check() {
		AmbientVariableTables tables = AmbientVariableTables.getTables();
		setExprType(tables.checkTypeTable("$float"));
	}

	@Override
	public String generateXML() {
		return "\\val{\\sival{" + value + ",0},\\unit{0,0,0}}";
	}

	@Override
	public String generateLatex() {
		return " " + value + " ";
	}

}

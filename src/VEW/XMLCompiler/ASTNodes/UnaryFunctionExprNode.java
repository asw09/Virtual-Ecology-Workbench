package VEW.XMLCompiler.ASTNodes;

import VEW.Planktonica2.model.Type;
import VEW.Planktonica2.model.VarietyType;


public class UnaryFunctionExprNode extends RuleNode {

	private UnaryExprFunction function;
	private ExprNode expArg;

	public UnaryFunctionExprNode(UnaryExprFunction function, ExprNode expArg) {
		this.function = function;
		this.expArg = expArg;
	}
	
	@Override
	public void check() {
		//This may need to change if any more unaryFunctions with expr args are added
		//Im considering changing this into a Divide node
		expArg.check();
		Type expType = expArg.getExprType();
		if (expType instanceof VarietyType) {
			CommonTreeWalker.add_exception(
					new SemanticCheckException("The expression for the number to divide to must be scalar",
							line_number));
		}

	}

	@Override
	public String generateXML() {
		String func = "";
		switch (function) {
		case DIVIDE : func = "divide"; break;
		}
		return "\\" + func + "{" + expArg.generateXML() + "}";
	}
	
	public String generateLatex() {
		String func = "???";
		switch (function) {
		case DIVIDE : func = " divide "; break;
		}
		if (expArg != null)
			return func + "(" + expArg.generateLatex() + ")";
		return func + "(???)";
	}

}

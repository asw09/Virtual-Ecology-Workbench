package VEW.Planktonica2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import VEW.Common.XML.XMLTag;
import VEW.Planktonica2.ControllerStructure.SelectableItem;
import VEW.XMLCompiler.ASTNodes.AmbientVariableTables;
import VEW.XMLCompiler.ASTNodes.SymbolTable;

public abstract class Catagory implements SelectableItem, BuildFromXML, BuildToXML {
	
	protected String name;
	
	protected ArrayList <Function> functions;
	
	protected SymbolTable<StateVariable> stateVarTable;
	protected SymbolTable<Parameter> paramTable;
	protected SymbolTable<Local> localVarTable;
	protected SymbolTable<VarietyVariable> varietyStateTable;
	protected SymbolTable<VarietyParameter> varietyParamTable;
	protected SymbolTable<VarietyConcentration> varietyConcTable;
	protected SymbolTable<VarietyLocal> varietyLocalTable;
	
	public Catagory() {
		
		functions = new ArrayList<Function>();
		
		initialiseTables();
	}

	private void initialiseTables() {
		stateVarTable = new SymbolTable<StateVariable>();
		paramTable = new SymbolTable<Parameter>();
		localVarTable = new SymbolTable<Local>();
		varietyStateTable = new SymbolTable<VarietyVariable>();
		varietyParamTable = new SymbolTable<VarietyParameter>();
		varietyConcTable = new SymbolTable<VarietyConcentration>();
		varietyLocalTable = new SymbolTable<VarietyLocal>();
		
	}
	
	public VariableType checkAssignableVariableTables(String variableName) {
		StateVariable sVar = checkStateVariableTable(variableName);
		if (sVar != null) return sVar;
		Local localVar = checkLocalVarTable(variableName);
		if (localVar != null) return localVar;
		VarietyLocal varLocal = checkVarietyLocalTable(variableName);
		if (varLocal != null) return varLocal;
		VarietyVariable varState = checkVarietyStateTable(variableName);
		return varState;
	}
	
	public VariableType checkAccessableVariableTable(String variableName) {
		VariableType var = checkAssignableVariableTables(variableName);
		if (var != null) return var;
		VarietyConcentration conc = checkVarietyConcTable(variableName);
		if (conc != null) return conc;
		AmbientVariableTables tables = AmbientVariableTables.getTables();
		var = tables.checkGlobalVariableTables(variableName);
		return var;
	}
	
	public VariableType checkAllVariableTables(String variableName) {
		VariableType var = checkAccessableVariableTable(variableName);
		if (var != null) return var;
		var = checkParameterTable(variableName);
		if (var != null) return var;
		var = checkVarietyParamTable(variableName);
		return var;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addToVarietyConcTable(VarietyConcentration conc) {
		varietyConcTable.put(conc.getName(), conc);
	}
	
	public VarietyConcentration checkVarietyConcTable(String conc) {
		return varietyConcTable.get(conc);
	}
	
	
	
	public void addToStateVarTable(StateVariable var) {
		stateVarTable.put(var.getName(), var);
	}
	
	public StateVariable checkStateVariableTable(String varName) {
		return stateVarTable.get(varName);
	}
	
	
	
	public void addToParamTable(Parameter param) {
		paramTable.put(param.getName(), param);
	}
	
	public Parameter checkParameterTable(String paramName) {
		return paramTable.get(paramName);
	}
	
	
	
	public void addToLocalTable(Local local) {
		localVarTable.put(local.getName(), local);
	}
	
	public Local checkLocalVarTable(String localName) {
		return localVarTable.get(localName);
	}
	
	
	
	public void addToVarietyStateTable(VarietyVariable var) {
		varietyStateTable.put(var.getName(), var);
	}
	
	public VarietyVariable checkVarietyStateTable(String varietyName) {
		return varietyStateTable.get(varietyName);
	}
	
	
	
	public void addToVarietyParamTable(VarietyParameter varParam) {
		varietyParamTable.put(varParam.getName(), varParam);
	}
	
	public VarietyParameter checkVarietyParamTable(String paramName) {
		return varietyParamTable.get(paramName);
	}
	
	
	
	public void addToVarietyLocalTable(VarietyLocal varLocal) {
		varietyLocalTable.put(varLocal.getName(), varLocal);
	}
	
	public VarietyLocal checkVarietyLocalTable(String localName) {
		return varietyLocalTable.get(localName);
	}

	public void setFunctions(ArrayList<Function> functions) {
		this.functions = functions;
	}
	public ArrayList<Function> getFunctions() {
		return functions;
	}
	@Override
	public int getNoFunctions() {
		return functions.size();
	}
	@Override
	public Function getFunctionAtIndex(int functionNo) {
		return this.functions.get(functionNo);
	}
	
	
	public String[] get_state_vars() {
		Object[] vars = stateVarTable.keySet().toArray();
		String [] all_vars = new String[vars.length];
		for (int i = 0; i < all_vars.length; i++) {
			all_vars[i] = (String) vars[i];
		}
		return all_vars;
	}
	
	public String[] get_params() {
		Object[] vars = paramTable.keySet().toArray();
		String [] all_vars = new String[vars.length];
		for (int i = 0; i < all_vars.length; i++) {
			all_vars[i] = (String) vars[i];
		}
		return all_vars;
	}

	public String[] get_local_vars() {
		Object[] vars = localVarTable.keySet().toArray();
		String [] all_vars = new String[vars.length];
		for (int i = 0; i < all_vars.length; i++) {
			all_vars[i] = (String) vars[i];
		}
		return all_vars;
	}

	public String[] get_variety_states() {
		Object[] vars = varietyStateTable.keySet().toArray();
		String [] all_vars = new String[vars.length];
		for (int i = 0; i < all_vars.length; i++) {
			all_vars[i] = (String) vars[i];
		}
		return all_vars;
	}

	public String[] get_variety_params() {
		Object[] vars = varietyParamTable.keySet().toArray();
		String [] all_vars = new String[vars.length];
		for (int i = 0; i < all_vars.length; i++) {
			all_vars[i] = (String) vars[i];
		}
		return all_vars;
	}

	public String[] get_variety_concs() {
		Object[] vars = varietyConcTable.keySet().toArray();
		String [] all_vars = new String[vars.length];
		for (int i = 0; i < all_vars.length; i++) {
			all_vars[i] = (String) vars[i];
		}
		return all_vars;
	}

	public String[] get_variety_locals() {
		Object[] vars = varietyLocalTable.keySet().toArray();
		String [] all_vars = new String[vars.length];
		for (int i = 0; i < all_vars.length; i++) {
			all_vars[i] = (String) vars[i];
		}
		return all_vars;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	public XMLTag buildToXML() {
		XMLTag tag = new XMLTag("placeholder");
		tag.addTag(new XMLTag("name", name));
		for(Function f: functions) {
			tag.addTag(f.buildToXML());
		}
		buildVariableTableToXML(tag, stateVarTable);
		buildVariableTableToXML(tag, paramTable);
		buildVariableTableToXML(tag, localVarTable);
		buildVariableTableToXML(tag, varietyStateTable);
		buildVariableTableToXML(tag, varietyParamTable);
		buildVariableTableToXML(tag, varietyConcTable);
		buildVariableTableToXML(tag, varietyLocalTable);
		return tag;
	}
	
	private <V extends VariableType> void buildVariableTableToXML(XMLTag tag, SymbolTable<V> table) {
		Collection<V> vals = table.values();
		Iterator<V> iter = vals.iterator();
		while(iter.hasNext()) {
			V var = iter.next();
			tag.addTag(var.buildToXML());
		}
		
	}
}

package VEW.Planktonica2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import VEW.Common.XML.XMLTag;


public abstract class VariableType implements BuildFromXML, BuildToXML {

	protected Catagory parentCatagory;
	private String name;
	private String desc;
	private Float value;
	private Type type;
	private Integer hist;
	private Collection<Unit> units;
	private boolean assignedTo;
	private boolean readFrom;
	
	public VariableType(Catagory parentCatagory, Type type) {
		this.parentCatagory = parentCatagory;
		this.type = type;
		this.assignedTo = false;
		this.readFrom = false;
	}
	
	public VariableType(Catagory parentCatagory) {
		this.parentCatagory = parentCatagory;
		this.assignedTo = false;
		this.readFrom = false;
	}
	
	public VariableType(String name, String desc, Type type, Collection<Unit> units) {
		this.name = name;
		this.desc = desc;
		this.type = type;
		this.units = units;
	}
	
	
	@Override
	public BuildFromXML build (XMLTag tag) {
		
		varBuild(tag);
		
		return this;
	}
	
	protected void varBuild (XMLTag tag) {
		
		XMLTag nameTag = tag.getTag(XMLTagEnum.NAME.xmlTag());
		if (nameTag != null) {
			this.name = nameTag.getValue();
		}
		
		XMLTag descTag = tag.getTag(XMLTagEnum.DESCRIPTION.xmlTag());
		if (descTag != null) {
			this.desc = descTag.getValue();
		}
		
		XMLTag valueTag = tag.getTag(XMLTagEnum.VALUE.xmlTag());
		if (valueTag != null) {
			String v = valueTag.getValue();
			
			if (v != null) {
				this.value = Float.valueOf(v);
			}
		}
		
		
		XMLTag histTag = tag.getTag(XMLTagEnum.HIST.xmlTag());
		if (histTag != null) {
			String v = histTag.getValue();
			
			if (v != null) {
				this.hist = Integer.valueOf(v);
			}
		}
		
		units = new ArrayList<Unit> ();
		XMLTag units = tag.getTag(XMLTagEnum.UNIT.xmlTag());
		if (units != null) {
			String unitString = units.getValue();
			if (unitString != null) {
				String [] individualUnits = unitString.split("(,)");
				// units are always in multiples of 3. checks this
				if (individualUnits.length % 3 == 0) {
					
					String [] unit = new String [3];
					int offset = 0;
					for (int i = 0; i < individualUnits.length ; i++) {
						
						unit[offset] = individualUnits[i];
						
						if (offset >= 2) {
							/*
							 * Checks for null on broken models, because some units seem to have
							 * a null size value and possibly a null exponent value.
							 */
							if (unit[0].equals("null")) {
								unit[0] = "0";
							} else if (unit[2].equals("null")) {
								unit[2] = "0";
							}
							Unit u = new Unit (Integer.valueOf(unit[0]), unit[1], Integer.valueOf(unit[2]));
							this.units.add(u);
							offset = 0;
							unit = new String [3];
							
						} else {
							offset++;
						}
						
					}				
				}
			}
		}
	}

	public XMLTag buildToXML() {
		XMLTag tag = new XMLTag("placeholder");
		tag.addTag(new XMLTag("name", name));
		if (desc != null)
			tag.addTag(new XMLTag("desc", desc));
		if (value != null)
			tag.addTag(new XMLTag("value", Float.toString(value)));
		if (hist != null)
			tag.addTag(new XMLTag("hist", Integer.toString(hist)));
		buildUnitXML(tag);
		return tag;
	}
	
	
	private void buildUnitXML(XMLTag tag) {
		String unitString = "";
		Iterator<Unit> unitIter = units.iterator();
		while(unitIter.hasNext()) {
			Unit u = unitIter.next();
			unitString += u.toString() + ",";
		}
		unitString = unitString.substring(0, unitString.length()-1);
		tag.addTag(new XMLTag("unit", unitString));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public int getHist() {
		return hist;
	}

	public void setHist(int hist) {
		this.hist = hist;
	}

	public Collection<Unit> getUnits() {
		return units;
	}

	public void setUnits(Collection<Unit> units) {
		this.units = units;
	}
	
	public Type getVarType() {
		return type;
	}
	
	public void setVarType(Type type) {
		this.type = type;
	}
	
	public boolean isAssignedTo() {
		return assignedTo;
	}
	
	public void setAssigned(boolean assigned) {
		assignedTo = assigned;
	}
	
	public boolean isReadFrom() {
		return readFrom;
	}
	
	public void setRead(boolean read) {
		readFrom = read;
	}
	
	
	
}

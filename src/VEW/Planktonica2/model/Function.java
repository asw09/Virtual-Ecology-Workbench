package VEW.Planktonica2.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import VEW.Common.XML.XMLTag;

public class Function implements BuildFromXML, BuildToXML {

	private String name;
	
	private Collection<Stage> calledIn;
	private Collection<Stage> availableStages;
	
	private String archiveName;
	private String comment;
	private String author;
	
	private String parentName;
	
	private Collection <Equation> equations;
	private String source_file_path;

	
	public Function(Collection<Stage> stages, String file_path, String parent) {
		this.source_file_path = get_path(file_path);
		this.availableStages = stages;
		this.setParentName(parent);
	}

	public Function(String file_path, String parent) {
		this.source_file_path = get_path(file_path);
		this.availableStages = null;
		this.setParentName(parent);
	}

	
	@Override
	public BuildFromXML build(XMLTag tag) {
		
		XMLTag nameTag = tag.getTag(XMLTagEnum.NAME.xmlTag());
		if (nameTag != null) {
			this.name = nameTag.getValue();
		}
		
		XMLTag [] xmlTags = null;
		
		if (availableStages != null) {
			xmlTags = tag.getTags(XMLTagEnum.CALLED_IN.xmlTag());
			this.calledIn = new ArrayList<Stage> (xmlTags.length);

			for (XMLTag t : xmlTags) {

				Stage s = getStageFromList(t.getValue());

				if (s != null) {
					calledIn.add(s);
				}
				//TODO: if stage does not exist thrown an exception
			}
		}
		
		
		xmlTags = tag.getTags(XMLTagEnum.EQUATION.xmlTag());
		this.equations = new ArrayList <Equation> (xmlTags.length);
		for (XMLTag t : xmlTags) {
			Equation e = new Equation();
			e.build(t);
			equations.add(e);
		}
		
		XMLTag authorTag = tag.getTag(XMLTagEnum.AUTHOR.xmlTag());
		if (authorTag != null) {
			this.author = authorTag.getValue();
		}
		
		XMLTag commentTag = tag.getTag(XMLTagEnum.COMMENT.xmlTag());
		if (commentTag != null) {
			this.comment = commentTag.getValue();
		}
		
		XMLTag archiveTag = tag.getTag(XMLTagEnum.ARCHIVE_NAME.xmlTag());
		if (archiveTag != null) {
			this.archiveName = archiveTag.getValue();
		}
		
		
		return this;
	}
	
	private Stage getStageFromList (String name) {
		
		if (availableStages == null || name == null)
			return null;
		
		for (Stage s : availableStages) {
			if (s.getName().equals(name)) {
				return s;
			}
		}
		
		return null;
		
	}

	/**
	 * Determines whether the given stage has been calledIn the current function
	 * 
	 * @param called
	 * @return true if stage found
	 */
	public boolean isCalledIn (Stage called) {
		for (Stage s : calledIn) {
			if (s.equals(called)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether the given stage has been calledIn the current function
	 * 
	 * @param name name of stage to be found
	 * @return Stage where Stage.getName == name
	 */
	public Stage isCalledIn (String name) {
		for (Stage s : calledIn) {
			if (s.getName().equals(name)) {
				return s;
			}
		}
		
		return null;
	}
	
	public void addToCalledIn(Stage selected) {
		if (!isCalledIn(selected)) {
			calledIn.add(selected);
		}
		
	}
	
	public void removeFromCalledIn(Stage selected) {
		
		calledIn.remove(selected);
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Stage> getCalledIn() {
		return calledIn;
	}

	public void setCalledIn(Collection<Stage> calledIn) {
		this.calledIn = calledIn;
	}

	public String getArchiveName() {
		return archiveName;
	}

	public void setArchiveName(String archiveName) {
		this.archiveName = archiveName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Collection<Equation> getEquations() {
		return equations;
	}

	public void setEquations(Collection<Equation> equations) {
		this.equations = equations;
	}

	public void setSource_code(String source_code) {
		this.source_file_path = source_code;
	}

	public String getSource_code() {
		return source_file_path;
	}

	private String get_path(String filePath) {
		filePath = filePath.substring(0, filePath.lastIndexOf('\\'));
		filePath += "\\";
		return filePath;
	}

	@Override
	public XMLTag buildToXML() {
		XMLTag funcTag = new XMLTag("function");
		funcTag.addTag("name", name);
		Iterator<Equation> eqIter = equations.iterator();
		while (eqIter.hasNext()) {
			Equation eq = eqIter.next();
			funcTag.addTag(eq.buildToXML());
		}
		Iterator<Stage> stageIter = calledIn.iterator();
		while (stageIter.hasNext()) {
			Stage stage = stageIter.next();
			funcTag.addTag(new XMLTag("calledin", stage.getName()));
		}
		if (archiveName != null)
			funcTag.addTag(new XMLTag("archivename", archiveName));
		if (comment != null)
			funcTag.addTag(new XMLTag("comment", comment));
		if (author != null)
			funcTag.addTag(new XMLTag("author", author));
		return funcTag;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentName() {
		return parentName;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	
	
	
}

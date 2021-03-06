package businessPlan;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class BusinessPlan implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8935223403182446534L;
	public Section root;
	public String department;
	public int year;
	public boolean isEditable;
	

	@Override
	public String toString() {
		return "BusinessPlan [root=" + root + ", department=" + department + ", year=" + year + ", isEditable="
				+ isEditable + "]";
	}

	public abstract void addSection(Section parent);// the only abstract method
	//if needed in the future, we may change the abstract method and let it throw Exceptions, but for now
	//we think print out is enough. And we also may simplify it. 

	public void deleteSection(Section child)
	{
		// check the node can be deleted or not
		// delete the whole branch
		// in order to keep a complete structure of the tree, we need to make sure that
		// except for the last section, all the other sections must have at least one
		// child.
		//为了保证整个plan的完整性
		//如果只有一个child 是不能删的
		Section parent = child.getParent();
		ArrayList<Section> children = parent.getChildren();
		int size = children.size();
		if (size != 1)
		{
			parent.deleteChild(child);
		} else
		{
			System.out.println("ERROR: THIS SECTION CANNOT BE DELETED! ! !");
		}

	}

	// encode the business plan to XML file
	public void encodeToXML(String fileName)
	{
		final String SERIALIZED_FILE_NAME = fileName;

		XMLEncoder encoder = null;
		try
		{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(SERIALIZED_FILE_NAME)));
		} 
		catch (NullPointerException | FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File");
		}
		encoder.writeObject(this);
		encoder.close();
	}

	// decode the business plan from a XML file
	public BusinessPlan decodeFromXML(String fileName)
	{

		final String SERIALIZED_FILE_NAME = fileName;
		XMLDecoder decoder = null;
		try
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
		} 
		catch (NullPointerException | FileNotFoundException e)
		{
			System.out.println("ERROR: File not found");
		}
		BusinessPlan plan = (BusinessPlan) decoder.readObject();
		System.out.println(plan);
		return plan;
	}

	// getter of root for XML
	public Section getRoot()
	{
		return root;
	}

	public void setRoot(Section root)
	{
		this.root = root;
	}

}

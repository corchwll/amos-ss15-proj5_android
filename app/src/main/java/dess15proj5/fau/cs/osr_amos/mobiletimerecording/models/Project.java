package dess15proj5.fau.cs.osr_amos.mobiletimerecording.models;


public class Project
{
	private long id;
	private String name;
	private boolean isDisplayed;
	private boolean isUsed;
	private boolean isArchived;

	/**
	 * Returns the project id.
	 *
	 * @methodtype get method
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Sets the project id to the given one if greater than zero.
	 *
	 * @methodtype set method
	 * @pre id > 0
	 * @post id successfully set
	 */
	public void setId(long id)
	{
		assert id > 0L: "project id has to be greater than zero!";
		this.id = id;
	}

	/**
	 * Returns the name of the project.
	 *
	 * @methodtype get method
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the project if not null.
	 *
	 * @methodtype set method
	 * @pre name != null
	 * @post name successfully set
	 */
	public void setName(String name)
	{
		assert name != null: "project name should not be null!";
		this.name = name;
	}

	/**
	 * Returns whether this project is displayed or not.
	 *
	 * @methodtype boolean query method
	 */
	public boolean isDisplayed()
	{
		return isDisplayed;
	}

	/**
	 * Sets whether this project is displayed or not.
	 *
	 * @methodtype set method
	 */
	public void setIsDisplayed(boolean isDisplayed)
	{
		this.isDisplayed = isDisplayed;
	}

	/**
	 * Returns whether this project is used or not.
	 *
	 * @methodtype boolean query method
	 */
	public boolean isUsed()
	{
		return isUsed;
	}

	/**
	 * Sets whether this project is used or not.
	 *
	 * @methodtype set method
	 */
	public void setIsUsed(boolean isUsed)
	{
		this.isUsed = isUsed;
	}

	/**
	 * Returns whether this project is archived or not.
	 *
	 * @methodtype boolean query method
	 */
	public boolean isArchived()
	{
		return isArchived;
	}

	/**
	 * Sets whether this project is archived or not.
	 *
	 * @methodtype set method
	 */
	public void setIsArchived(boolean isArchived)
	{
		this.isArchived = isArchived;
	}
}

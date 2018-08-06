package models;
import java.util.List;
/**
 * userProfile class- Profile Information of user.
 * @version 1.0
 * @author Gurpreet Lotey
 */
public class userProfile {
	 String name;
	 String profileName;
	 String Url;
	 String description;
	 String location;
	 List<String> timeline;

	
	/**
	 * Gets name.
	 * @author Gurpreet Lotey
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets name.
	 * @author Gurpreet Lotey
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets profileName.
	 * @author Gurpreet Lotey
	 * @return String 
	 */
	public String getUserProfileName() {
		return profileName;
	}
	/**
	 * Sets profileName.
	 * @author Gurpreet Lotey
	 * @param profileName 
	 */
	public void setUserProfileName(String profileName) {
		this.profileName = profileName;
	}
	/**
	 * Gets Url.
	 * @author Gurpreet Lotey
	 * @return String 
	 */
	public String getUrl() {
		return Url;
	}
	/**
	 * Sets Url.
	 * @author Gurpreet Lotey
	 * @param Url 
	 */
	public void setUrl(String Url) {
		this.Url = Url;
	}
	
	/**
	 * Gets description.
	 * @author Gurpreet Lotey
	 * @return String 
	 */
	public String getDescription() {
		return description;
	}
	/**
	 *Sets description.
	 * @author Gurpreet Lotey
	 * @param description 
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Ges location.
	 * @author Gurpreet Lotey
	 * @return String 
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * Sets location.
	 * @author Gurpreet Lotey
	 * @param location 
	 */
	
	public void setLocation(String location) {
		this.location = location;
	}	
	/**
	 * Gets timeline.
	 * @author Gurpreet Lotey
	 * @return List
	 */
	public List<String> getTimeline() {
		return timeline;
	}
	
	/**
	 * Sets timeline.
	 * @author Gurpreet Lotey
	 * @param timeline 
	 */
	public void setTimeline(List<String> timeline) {
		this.timeline = timeline;
	}
	
}
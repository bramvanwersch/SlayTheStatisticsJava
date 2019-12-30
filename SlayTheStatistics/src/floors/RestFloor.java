package floors;

import java.util.Map;

public class RestFloor extends GeneralFloor {

	public RestFloor(Map<String, String> floorDict, Floor previousFloor, String floorNo) {
		super(floorDict, previousFloor, floorNo);
	}
	
	public String getActivity() {
		return getStringValue("key");
	}
	
	//TODO: see if there are more options that will give some data.
	public String getData() {
		if (getActivity().equals("SMITH")) {
			return getStringValue("data");
		}
		else {
			//temporary return for figuring out if there are more data entries possible.
			return "not searching for data because data key is: (" + getStringValue("data") + ")";
		}
	}
	
	public Map<String,String> getFloorMap(){
		Map<String, String> floorMap = getBaseFloorMap();
		floorMap.put("activity", getActivity());
		if (!getData().equals("!")) {
			floorMap.put("data", getData());}
		floorMap.put("special", String.format("Activity: %s (%s)", getActivity(), getData()));
		return floorMap;	
	}
	
	@Override
	public String getText() {
		String text = starterText();
		text += String.format("Campfire activity: %s", getActivity());
		if (getActivity().equals("SMITH")) {
			text += String.format(" (%s)", getData());
		}
		text += "\n";
		return text;
	}
	

}

package karnaugh;

public class Element {
	
	//--for states
	public static final int _0 = 0;
	public static final int _1 = 1;
	public static final int _NONE = -1;
	public static final int _INVALID = -2;
	
	public int state;
	public int index;
	
	public Element(int index){
		this.index = index;
		this.state = _NONE;
	}
	
	//expression visual'.tool
	public void setUp(String expression){
		
		if(state == _INVALID){
			return;
		}
		
		if(expression.contains(KarnaughMap.values[index])){
			
			String helper[] = expression.split("\\.");
			for (int i = 0; i < helper.length; i++) {
				//
				if(helper[i].contains(KarnaughMap.values[index])){
					//setting value
					if(helper[i].contains("'")){
						state = _0;
					}
					else{
						state = _1;
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + KarnaughMap.values[index] + ")" + Integer.toString(state);
	}
	
}

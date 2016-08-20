import java.util.ArrayList;


public class Expression {
	//--as index 0
	public Element A;
	//--as index 1
	public Element B;
	//--as index 2
	public Element C;
	//--as index 3
	public Element D;
	
	public Expression(){
		A = new Element(0);
		B = new Element(1);
		C = new Element(2);
		D = new Element(3);
	}
	
	public Expression(String expression){
		
		this();
		
		//--
		if(KarnaughMap.numEle == 2){
			B.state = Element._INVALID;
			D.state = Element._INVALID;
		}
		else if(KarnaughMap.numEle == 3){
			D.state = Element._INVALID;
		}
		//--
		A.setUp(expression);
		B.setUp(expression);
		C.setUp(expression);
		D.setUp(expression);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "A:" + A + " B:" + B + " C:" + C + " D:" + D;
	}
	
	public Map toMap(){
		
		Map m = new Map();
		//--detech row
		if(B.state != Element._INVALID){
			//--
			if(A.state == Element._NONE && B.state == Element._NONE){
				m.row = 1;
				m.height = 4;
			}
			else if(A.state == Element._NONE){
				//Check B
				m.row = B.state == Element._0 ? 3 : 1;
				m.height = 2;
			}
			else if(B.state == Element._NONE){
				//Check A
				m.row = A.state == Element._0 ? 0 : 2;
				m.height = 2;
			}
			else{
				m.row = A.state == Element._0 ? 0 : 2;
				if(A.state != B.state){
					m.row++;
				}
				m.height = 1;
			}
		}
		else{
			//When there is no B component
			if(A.state == Element._NONE){
				m.row = 0;
				m.height = 2;
			}
			else{
				m.row = A.state == Element._0 ? 0 : 1;
				m.height = 1;
			}
		}
		//Detech column
		//--
		if(D.state != Element._INVALID){
			//--
			if(C.state == Element._NONE && D.state == Element._NONE){
				m.column = 1;
				m.width = 4;
			}
			else if(C.state == Element._NONE){
				//Check B
				m.column = D.state == Element._0 ? 3 : 1;
				m.width = 2;
			}
			else if(D.state == Element._NONE){
				//Check A
				m.column = C.state == Element._0 ? 0 : 2;
				m.width = 2;
			}
			else{
				m.column = C.state == Element._0 ? 0 : 2;
				if(C.state != D.state){
					m.column++;
				}
				m.width = 1;
			}
			//--
		}
		else{
			//When there is no D component
			if(C.state == Element._NONE){
				m.column = 0;
				m.width = 2;
			}
			else{
				m.column = C.state == Element._0 ? 0 : 1;
				m.width = 1;
			}
		}
		
		return m;
	}

	public String getExpression() {
		//Printed expression
		ArrayList<String> list = new ArrayList<String>();
		String ex = "";
		
		if(A.state == Element._0){
			list.add(KarnaughMap.values[A.index] + "'");
		}
		else if(A.state == Element._1){
			list.add(KarnaughMap.values[A.index]);
		}
		
		if(B.state == Element._0){
			list.add(KarnaughMap.values[B.index] + "'");
		}
		else if(B.state == Element._1){
			list.add(KarnaughMap.values[B.index]);
		}
		
		if(C.state == Element._0){
			list.add(KarnaughMap.values[C.index] + "'");
		}
		else if(C.state == Element._1){
			list.add(KarnaughMap.values[C.index]);
		}
		
		if(D.state == Element._0){
			list.add(KarnaughMap.values[D.index] + "'");
		}
		else if(D.state == Element._1){
			list.add(KarnaughMap.values[D.index]);
		}
		
		for (int i = 0; i < list.size(); i++) {
			ex += list.get(i);
			if(i != list.size() - 1){
				ex += ".";
			}
		}
		return ex;
	}
}


public class Node {

		String val;
		int count;
		Node left;
		Node rite;
		String lineno;
		
		public Node(String str) {
			val = str;
			count = 1;
			left = null;
			rite = null;
			lineno = "";
		}

		public void setval(String nuval) { val = nuval; }
		public String getval() { return val; }
		
		public Node getleft() { return left; }
		public Node getrite() { return rite; }
		
		public void setleft(Node l) { left = l; }
		public void setrite(Node r) { rite = r; }
		
		public void inc() { count++; }
		public void addlineno(int i) { lineno += " " + (i); }
		
		public void insert(String str, int lino) {
		    //int compare = word.compareTo(record.getWord());
		    str = str.toLowerCase();
			int c = check(val, str);
		    
		    if (c > 0) {
		    	
		    	if (left == null) {
		    		left = new Node(str);
		    		left.addlineno(lino);
		    	} 
		    
		    	else { left.insert(str, lino); }	
		    } 
		    
		    else if (c < 0) {
		    	if (rite == null) {
		    		rite = new Node(str);
		    		rite.addlineno(lino);
		    	}
		    	
		    	else { rite.insert(str, lino); }
		    } 
		    
		    else {
		      //record.increment(lineNum);
		      this.inc();
		      if ( lineno.endsWith((""+lino)) ) { /*DERP*/ } else { this.addlineno(lino); }
		    }
		}
		
		public int check(String a, String b) {
			
			int i = a.compareToIgnoreCase(b);
			if (i == 0) return 0;
			else if (i > 0) return 1;
			else return -1;

		}
		
	}

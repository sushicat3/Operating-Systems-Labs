import java.util.*;
import java.io.*;

public class Bitree {
	
	public Node root;
	
	public Bitree() {
    	root = null ;
	}
 
	public void insert(String str, int lino) {
		if (root == null) {
			root = new Node(str);
			root.addlineno(lino);
		} 
		else { root.insert(str, lino); }
	}
	
//>>>> Figure out where to put this method	
	public void node_prnt(Node n) { 
    	System.out.println( n.getval() + " ..... " + n.count + ":" + n.lineno); 
    } 

    public void in_order_prnt(Node n) {
    	if (n != null) {
    		in_order_prnt(n.left);
        	node_prnt(n);
        	in_order_prnt(n.rite);
    	}
    }

/*
    public void insert(String str, Node psuroot, int lineco ) {
    	
    	if (psuroot == null) { 
    		Node nuleaf = new Node(str); 
    		psuroot = nuleaf;
    		psuroot.addlineno(lineco);
    			node_prnt(psuroot);
    		return;
    	}
    	
    	else if (psuroot.getval() == "") { 
    		psuroot.setval(str); 
    		psuroot.addlineno(lineco); 
    			node_prnt(psuroot);
    		return; 
    	}
		
    	else if (check( psuroot.getval(), str) == 0) { 
    		psuroot.inc(); 
    		psuroot.addlineno(lineco); 
    			node_prnt(psuroot);
    		return; 
    	}
  	
    	else if (check( psuroot.getval(), str) == -1) { 
    		insert( str, psuroot.getleft(), lineco );
    	}

    	else if (check( psuroot.getval(), str) == 1) {
    		insert( str, psuroot.getrite(), lineco );
    	}

    }
*/


    
    public static void main(String[] args) {
    	
    	Bitree bt = new Bitree();

    	BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("text.txt"));
			String line;
			
	        try {
	        	int j = 1;
				while((line = br.readLine()) != null) {
						
						String[] words = line.split("[.\\,\\s\\?\\(\\)]+");
						
						for(int i = 0; i < words.length; i++){
							bt.insert(words[i], j);
						}
				j++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bt.in_order_prnt(bt.root);
    }
}



public class StringsSort  {
	/**
	 * Die heapify()Methode pr�ft, ob ein Kindknoten gr��er als der Elternknoten
	 * ist. Ist dies der Fall, wird das �bergeordnete Element gegen das gr��ere
	 * untergeordnete Element ausgetauscht und der Vorgang auf dem untergeordneten
	 * Knoten wiederholt.
	 * 
	 * @param array
	 * @param length l�nge des Arrays
	 * @param parentPos : die ParentPosition, die ich �berpr�fe ob er die Max-Heap
	 * oder MIN-Heap Bedingungen erf�llt 
	 */
	public void heapify(String [] array, int length, int parentPos) {
		
		while(true) {
			// die formel zum bestimmen von link und recht kinder
			 int leftChildPos = parentPos * 2 + 1; // +1 denn das array beginnt bei 0
			 int rightChildPos = parentPos * 2 + 2;
			
			 // Find the largest element
			 int max = parentPos; // wir nehmen an, dass die �bergebene Parm ist das gr��te
			 
			 if(leftChildPos < length && array[leftChildPos].compareToIgnoreCase(array[max]) > 0 ) {
				 max=leftChildPos; // das linke kind ist gr��te Knote jetzt 
			 }
			 if(rightChildPos < length && array[rightChildPos].compareToIgnoreCase(array[max]) >0) {
				max=rightChildPos;// das rechte kind ist gr��te Knote jetzt 
			 }
			// largestPos is now either parentPos, leftChildPos or rightChildPos.
			 // If it's the parent, we're done
			 if(max == parentPos) {
				 break;
			 }
			 
			// If it's not the parent, then switch!
			swap(array, parentPos, max);

			
			// ... and fix again starting at the child we moved the parent to
		    parentPos = max;
		}
	}

	/**
	 * Die buildHeap()Methode ruft heapify()f�r jeden �bergeordneten Knoten auf,
	 * beginnend mit dem letzten, und �bergibt dieser Methode das Array, die L�nge
	 * des Subarrays, das den Heap darstellt, und die Position des �bergeordneten
	 * Knotens, an heapify()der gestartet werden soll:
	 * @param elements
	 */
	public void buildHeap(String[] elements) {
		// "Find" the last parent node
		int lastParentNode = elements.length / 2 - 1;
		
		for (int i = lastParentNode; i >= 0; i--) {// die beginnt ab letzer Elternknoten und ruft heapify, um maxheap zu bilden
		    heapify(elements, elements.length, i);
		  }
	}

	/**
	 * Die sort()Methode ruft zuerst buildHeap()auf, um anf�nglich den Heap zu
	 * erstellen.
	 * 
	 * In der folgenden Schleife swapToPos iteriert die Variable r�ckw�rts vom Ende
	 * des Arrays zu seinem zweiten Feld. Im Schleifenk�rper wird das erste Element
	 * mit dem an der swapToPosPosition vertauscht, und dann heapify()wird die
	 * Methode auf dem Subarray bis (ausschlie�lich) der swapToPosPosition
	 * aufgerufen
	 * 
	 * @param elements
	 */
	  public void heapSort(String [] elements) {
		  buildHeap(elements); // erste mal haep aufbauen
		  
		  for (int swapToPos = elements.length - 1; swapToPos > 0; swapToPos--) {// der root muss am ende des array
			 
			  swap(elements,0,swapToPos);//// Move root(0) to end(swapToPos)
			  
			  //heapify aufrufen um wieder der heap als maxheap zu bauen
			  heapify(elements, swapToPos, 0);
		  }
	  }
	  

	// String tauchen
	 public void swap(String [] elements, int zahl_1, int zahl_2) {
		  String temp=elements[zahl_1];
		  elements[zahl_1]=elements[zahl_2];
		  elements[zahl_2]=temp;
	  }
	 /**
	  * array Elemente ausgeben
	  * @param array
	  */
	
	 void print(String [] array) {
		System.out.print("{");
		for(int i=0; i<array.length;i++) {
			System.out.print(array[i]);
			if(i<array.length-1) {/* array.length-1 ? damit nicht am Ende des Felds eine Komma steht
			                       z.b 1,2,32,*/
				System.out.print(" , ");
			}
		}
		System.out.print("}");
	}
	public static void main(String[] args) {
		String [] array= {"y","D","B","X","H","a","c","b"};
		StringsSort heap= new StringsSort();
		heap.heapSort(array);
		heap.print(array);
	
	}

}

package ule.edi.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

import org.junit.internal.Throwables;

/**
 * Árbol binario de búsqueda (binary search tree, BST).
 * 
 * El código fuente está en UTF-8, y la constante EMPTY_TREE_MARK definida en
 * AbstractTreeADT del proyecto API debería ser el símbolo de conjunto vacío: ∅
 * 
 * Si aparecen caracteres "raros", es porque el proyecto no está bien
 * configurado en Eclipse para usar esa codificación de caracteres.
 *
 * En el toString() que está ya implementado en AbstractTreeADT se usa el
 * formato:
 * 
 * Un árbol vacío se representa como "∅". Un árbol no vacío como "{(información
 * raíz), sub-árbol 1, sub-árbol 2, ...}".
 * 
 * Por ejemplo, {A, {B, ∅, ∅}, ∅} es un árbol binario con raíz "A" y un único
 * sub-árbol, a su izquierda, con raíz "B".
 * 
 * El método render() también representa un árbol, pero con otro formato; por
 * ejemplo, un árbol {M, {E, ∅, ∅}, {S, ∅, ∅}} se muestra como:
 * 
 * M | E | | ∅ | | ∅ | S | | ∅ | | ∅
 * 
 * Cualquier nodo puede llevar asociados pares (clave,valor) para adjuntar
 * información extra. Si es el caso, tanto toString() como render() mostrarán
 * los pares asociados a cada nodo.
 * 
 * Con {@link #setTag(String, Object)} se inserta un par (clave,valor) y con
 * {@link #getTag(String)} se consulta.
 * 
 * 
 * Con <T extends Comparable<? super T>> se pide que exista un orden en los
 * elementos. Se necesita para poder comparar elementos al insertar.
 * 
 * Si se usara <T extends Comparable<T>> sería muy restrictivo; en su lugar se
 * permiten tipos que sean comparables no sólo con exactamente T sino también
 * con tipos por encima de T en la herencia.
 * 
 * @param <T> tipo de la información en cada nodo, comparable.
 */
public class BinarySearchTreeADTImpl<T extends Comparable<? super T>> extends AbstractBinaryTreeADT<T> {

	/**
	 * Devuelve el árbol binario de búsqueda izquierdo.
	 */
	protected BinarySearchTreeADTImpl<T> getLeftBST() {
		// El atributo leftSubtree es de tipo AbstractBinaryTreeADT<T> pero
		// aquí se sabe que es además de búsqueda binario
		//
		return (BinarySearchTreeADTImpl<T>) leftSubtree;
	}

	private void setLeftBST(BinarySearchTreeADTImpl<T> left) {
		this.leftSubtree = left;
	}

	/**
	 * Devuelve el árbol binario de búsqueda derecho.
	 */
	protected BinarySearchTreeADTImpl<T> getRightBST() {
		return (BinarySearchTreeADTImpl<T>) rightSubtree;
	}

	private void setRightBST(BinarySearchTreeADTImpl<T> right) {
		this.rightSubtree = right;
	}

	/**
	 * Árbol BST vacío
	 */
	public BinarySearchTreeADTImpl() {

		setContent(null);

		setLeftBST(null);
		setRightBST(null);
	}

	private BinarySearchTreeADTImpl<T> emptyBST() {
		return new BinarySearchTreeADTImpl<T>();
	}

	/**
	 * Inserta todos los elementos de una colección en el árbol.
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements valores a insertar.
	 */
	public void insert(Collection<T> elements) {
		Iterator<T> it = elements.iterator();
		boolean comenzar = true;
		while (it.hasNext()) { // Comprobamos que ningun elemento sea null
			if (it.next() == null) {
				comenzar = false;
			}
		}

		if (comenzar) {

			it = elements.iterator();

			ArrayList<Integer> list = new ArrayList<Integer>();

			while (it.hasNext()) { // Comprobamos que ningun elemento sea null
				insert(it.next());
			}

		}

	}

	/**
	 * Inserta todos los elementos de un array en el árbol.
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements elementos a insertar.
	 */
	public void insert(T... elements) {
		boolean comenzar = true;
		for (T elem : elements) { // Si encontramos un null no se ejecutara el algoritmo

			if (elem == null) {
				comenzar = false;
			}
		}

		if (comenzar) {

			for (T elem : elements) {

				insert(elem);

			}

		}

	}

	/**
	 * Inserta de forma recursiva (como hoja) un nuevo elemento en el árbol de
	 * búsqueda.
	 * 
	 * No se permiten elementos null. Si el elemento ya existe en el árbol NO lo
	 * inserta.
	 * 
	 * @param element valor a insertar.
	 */
	public void insert(T element) {
		BinarySearchTreeADTImpl<T> arbol = emptyBST();

		if (element == null) {
			throw new IllegalArgumentException("No se aceptan elementos nulos");
		}

		if (this.getContent() == null) { // caso para la primera raiz
			// System.out.println("Primera raiz");
			this.content = element; // Primera raiz
			this.leftSubtree = emptyBST(); // creamos los arboles laterales vacios
			this.rightSubtree = emptyBST();
		}

		// System.out.println("Elemento: " + element + " this.hoja: " + this.content);
		if (element.compareTo(this.content) == 1) { // Cuando el content es mayor que la raiz se va a la derecha

			if (this.getRightBST().content == null) { // si encontramo el arbol vacio se introduce

				arbol.content = element;
				arbol.leftSubtree = emptyBST();
				arbol.rightSubtree = emptyBST();

				this.setRightBST(arbol); // Introduzco el arbol con la raiz ya establecida

			} else { // si no se sigue buscando un hueco
				this.getRightBST().insert(element);
			}

		} else if (element.compareTo(this.content) == -1) { // Cuando el content es menor que la raiz se va a la
															// izquierda

			if (this.getLeftBST().content == null) { // si encontramo el arbol vacio se introduce

				arbol.setContent(element);
				arbol.setLeftBST(emptyBST());
				arbol.setRightBST(emptyBST());

				this.setLeftBST(arbol);// Introduzco el arbol con la raiz ya establecida

			} else { // si no se sigue buscando un hueco
				this.getLeftBST().insert(element);
			}
		}
	}

	/**
	 * Elimina los elementos de la colección del árbol.
	 */
	public void withdraw(Collection<T> elements) {
		// O todos o ninguno; si alguno es 'null', no se eliminará ningún elemento
		// TODO Implementar el método
	}

	/**
	 * Elimina los valores en un array del árbol.
	 */
	public void withdraw(T... elements) {
		// O todos o ninguno; si alguno es 'null', no se eliminará ningún elemento
		// TODO Implementar el método
	}

	/**
	 * Elimina un elemento del árbol.
	 * 
	 * @throws NoSuchElementException si el elemento a eliminar no está en el árbol
	 */
	public void withdraw(T element) {

		// Si el elemento tiene dos hijos, se tomará el criterio de sustituir el
		// elemento por el mayor de sus menores y eliminar el mayor de los menores.
		// TODO Implementar el método

		if (element == null) {
			throw new IllegalArgumentException("No se aceptan elementos nulos"); 
		}

		System.out.println("--------COMENZAMOS A COMPARAR DERECHA IZQUIERDA, EL CONTENT ACTUAL ES: " + this.content);
		if (element.compareTo(this.content) == 1 || element.compareTo(this.content) == 0) { // content derecha o igual
			System.out.println("DERECHA");
			System.out.println("COMPARAMOS " + element + "con " + this.content);

			if (element.compareTo(this.content) == 0) { // Llegamos al elemento

				System.out.println("El elemto encontrado es " + this.content);
				System.out.println("SU ARBOL DERECHO ES: " + this.getRightBST().content + "y su izquierdo es: "
						+ this.getLeftBST().content);

				if ((this.leftSubtree.content != null) && (this.rightSubtree.content != null)) {// El arbol tiene dos
																								// hijos
					System.out.println("El arbol tiene dos hijos");
					BinarySearchTreeADTImpl<T> aux = this.getLeftBST(); //El primero a la izquierda para ser el menores

					while (aux.getRightBST().content != null) {

						aux = aux.getRightBST(); // Nos movemos a la derecha

					}

					
					T delete = aux.content;
					System.out.println("el delete es " + delete);
					this.content = delete;
					getLeftBST().withdraw(delete);

				} else if ((this.leftSubtree.content != null) || (this.rightSubtree.content != null)) { // El arbol
																										// tiene un hijo
					System.out.println("El arbol tiene un hijo");

					if (this.leftSubtree.isEmpty()) { // Cuando el arbol izquierdo tiene no es null
						
						
						System.out.println("Su hijo es el derecho");
						this.content = this.getRightBST().content;
						
						if (this.getLeftBST().getLeftBST() != null) {
							this.leftSubtree = this.getLeftBST().getLeftBST();
						}

						if (this.getRightBST().getRightBST() != null) {
							this.rightSubtree = this.getRightBST().getRightBST();
						}
						
						
					} else {// Cuando el derecho no es null
						System.out.println("Su hijo es el izquierdo");

						this.content = this.getLeftBST().content;

						if (this.getLeftBST().getLeftBST() != null) {
							this.leftSubtree = this.getLeftBST().getLeftBST();
						}

						if (this.getRightBST().getRightBST() != null) {
							this.rightSubtree = this.getRightBST().getRightBST();
						}

					}

				} else if ((this.leftSubtree.content == null) && (this.rightSubtree.content == null)) { // no tiene
																										// hijos
					System.out.println("El arbol no tiene hijos");
					this.content = null;
					this.setRightBST(null);
					this.setLeftBST(null);
					System.out.println("elemento eliminado");

				} else if (this.content == null) { // Hemos llegado al final y el elemento no esta en el arbol
					throw new IllegalArgumentException("Elemento no encontrado");

				}

			} else {
				System.out.println("Bajamos al siguente elemento");
				getRightBST().withdraw(element);
			}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		} else if (element.compareTo(this.content) == -1  || element.compareTo(this.content) == 0) { // izquierda

			System.out.println("IZQUIERDA");
			System.out.println("COMPARAMOS " + element + "con " + this.content);

			if (element.compareTo(this.content) == 0) { // Llegamos al elemento

				System.out.println("El elemto encontrado es " + this.content);
				System.out.println("SU ARBOL DERECHO ES: " + this.getRightBST().content + "y su izquierdo es: "
						+ this.getLeftBST().content);

				if ((this.leftSubtree.content != null) && (this.rightSubtree.content != null)) {// El arbol tiene dos
																								// hijos
					System.out.println("El arbol tiene dos hijos");
					BinarySearchTreeADTImpl<T> aux = this.getLeftBST();// Uno a la izquierda

					while (aux.getRightBST().content != null) {

						aux = aux.getRightBST(); // Nos movemos a la derecha

					}

					T delete = aux.content;
					System.out.println("el delete es " + delete);
					this.content = delete;
					getLeftBST().withdraw(delete);

				} else if ((this.leftSubtree.content != null) || (this.rightSubtree.content != null)) { // El arbol
																										// tiene un hijo
					System.out.println("El arbol tiene un hijo");

					if (this.leftSubtree.isEmpty()) { // Cuando el arbol izquierdo tiene no es null
						System.out.println("Su hijo es el derecho");
						this.content = this.getLeftBST().content;
						if (this.getLeftBST().getLeftBST() != null) {
							this.leftSubtree = this.getLeftBST().getLeftBST();
						}

						if (this.getRightBST().getRightBST() != null) {
							this.rightSubtree = this.getRightBST().getRightBST();
						}
						// this.getLeftBST().content = null;

					} else {// Cuando el derecho no es null
						System.out.println("Su hijo es el izquierdo");

						this.content = this.getLeftBST().content;
						System.out.println(this.content);
						System.out.println(this.getLeftBST().toString());
						System.out.println(this.getRightBST().toString());

						if (this.getLeftBST().getLeftBST() != null) {
							this.leftSubtree = this.getLeftBST().getLeftBST();
						}

						if (this.getRightBST().getRightBST() != null) {
							this.rightSubtree = this.getRightBST().getRightBST();
						}

						System.out.println("-------");
						System.out.println(this.getLeftBST().toString());
						System.out.println(this.getRightBST().toString());
						// this.setRightBST(emptyBST());
					}

				} else if ((this.leftSubtree.content == null) && (this.rightSubtree.content == null)) { // no tiene
																										// hijos
					System.out.println("El arbol no tiene hijos");
					this.content = null;
					this.setRightBST(null);
					this.setLeftBST(null);
					System.out.println("elemento eliminado");

				} else if (this.content == null) { // Hemos llegado al final y el elemento no esta en el arbol
					throw new IllegalArgumentException("Elemento no encontrado");

				}

			} else {
				System.out.println("Bajamos al siguente elemento");
				getLeftBST().withdraw(element);
			}

		}
		System.out.println(this.toString());
		System.out.println("fin del metodo");
	}

	/**
	 * Devuelve el sub-árbol indicado. (para tests) path será el camino para obtener
	 * el sub-arbol. Está formado por 0 y 1. Si se codifica "bajar por la izquierda"
	 * como "0" y "bajar por la derecha" como "1", el camino desde un nodo N hasta
	 * un nodo M (en uno de sus sub-árboles) será la cadena de 0s y 1s que indica
	 * cómo llegar desde N hasta M.
	 *
	 * Se define también el camino vacío desde un nodo N hasta él mismo, como cadena
	 * vacía.
	 * 
	 * Si el subarbol no existe lanzará la excepción NoSuchElementException.
	 * 
	 * @param path
	 * @return
	 * @throws NoSuchElementException si el subarbol no existe
	 */
	public BinarySearchTreeADTImpl<T> getSubtreeWithPath(String path) {
		// TODO implementar el método

		return null;

	}

	/**
	 * Acumula en orden descendente, una lista con los pares 'padre-hijo' en este
	 * árbol.
	 * 
	 * Por ejemplo, sea un árbol "A":
	 * 
	 * {10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}
	 * 
	 * 10 | 5 | | 2 | | | ∅ | | | ∅ | | ∅ | 20 | | ∅ | | 30 | | | ∅ | | | ∅
	 * 
	 * el resultado sería una lista de cadenas:
	 * 
	 * [(20,30), (10,20), (10,5), (5,2)]
	 * 
	 * y además quedaría etiquetado como:
	 * 
	 * {10 [(descend, 3)], {5 [(descend, 4)], {2 [(descend, 5)], ∅, ∅}, ∅}, {20
	 * [(descend, 2)], ∅, {30 [(descend, 1)], ∅, ∅}}}
	 * 
	 * @param buffer lista con el resultado.
	 */
	public void parentChildPairsTagDescend(List<String> buffer) {

		// TODO Implementar el método
	}

	/**
	 * Importante: Solamente se debe recorrer el árbol una vez
	 * 
	 * Comprueba si los elementos de la lista coinciden con algún camino desde la
	 * raiz. Además, si existe algún camino que coincida con los elementos de la
	 * lista, los etiqueta en el árbol, numerandolos empezando por la raiz como 1.
	 * 
	 * Por ejemplo, el árbol
	 * 
	 * {50, {30, {10, ∅, ∅}, {40, ∅, ∅}}, {80, {60, ∅, ∅}, ∅}}
	 * 
	 * si path = [50, 30, 10]
	 * 
	 * devolvería true y el árbol quedaría así etiquetado:
	 * 
	 * {50 [(path, 1)], {30 [(path, 2)], {10 [(path, 3)], ∅, ∅}, {40, ∅, ∅}}, {80,
	 * {60, ∅, ∅}, ∅}}
	 * 
	 * Para el mismo árbol, si path es [50, 40] devolvería true y el árbol quedaría
	 * así etiquetado: {50 [(path, 1)], {30, {10, ∅, ∅}, {40 [(path, 2)], ∅, ∅}},
	 * {80, {60, ∅, ∅}, ∅}}
	 * 
	 * Para el mismo árbol, si path es [50, 80] devolvería false y el árbol no se
	 * etiqueta: {50, {30, {10, ∅, ∅}, {40, ∅, ∅}}, {80, {60, ∅, ∅}, ∅}}
	 * 
	 * 
	 * @return true si los elementos de la lista coinciden con algún camino desde la
	 *         raiz, falso si no es así
	 */
	public boolean isPathIn(List<T> path) {
		// TODO Implementar método
		return false;
	}

	/**
	 * 
	 * Etiqueta cada nodo con su posición en el recorrido en anchura, con la
	 * etiqueta "width"
	 * 
	 * Por ejemplo, el árbol
	 * 
	 * {50, {30, {10, ∅, ∅}, {40, ∅, ∅}}, {80, {60, ∅, ∅}, ∅}}
	 * 
	 * queda etiquetado como
	 * 
	 * {50 [(width, 1)], {30 [(width, 2)], {10 [(width, 4)], ∅, ∅},{40 [(width, 5)],
	 * ∅, ∅}}, {80 [(width, 3)], {60 [(width, 6)], ∅, ∅}, ∅}}
	 * 
	 * 
	 */
	public void tagWidth() {
		// TODO Implementar método
	}

	/**
	 * Devuelve un iterador que recorre los elementos del arbol en inorden (de menor
	 * a mayor)
	 * 
	 * Por ejemplo, con el árbol
	 * 
	 * {50, {30, {10, ∅, ∅}, {40, ∅, ∅}}, {80, {60, ∅, ∅}, ∅}}
	 * 
	 * y devolvería el iterador que recorrería los ndos en el orden: 10, 30, 40, 50,
	 * 60, 80
	 * 
	 * 
	 * 
	 * @return iterador para el recorrido inorden o ascendente
	 */
	public Iterator<T> iteratorInorden() {
		// TODO Implementar método
		return null;
	}

}

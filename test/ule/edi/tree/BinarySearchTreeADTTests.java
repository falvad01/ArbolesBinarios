package ule.edi.tree;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BinarySearchTreeADTTests {

	/*
	 * ∅
	 */
	private BinarySearchTreeADTImpl<Integer> TE = null;

	/*
	 * 1 | ∅ | 2 | | ∅ | | 3 | | | ∅ | | | 4 | | | | ∅ | | | | ∅
	 */
	private BinarySearchTreeADTImpl<Integer> T1234 = null;

	/*
	 * 4 | 3 | | 2 | | | 1 | | | | ∅ | | | | ∅ | | | ∅ | | ∅ | ∅
	 */
	private BinarySearchTreeADTImpl<Integer> T4321 = null;

	/*
	 * 50 | 20 | | 10 | | | ∅ | | | ∅ | | 30 | | | ∅ | | | ∅ | 80 | | 70 | | | ∅ | |
	 * | ∅ | | 90 | | | ∅ | | | ∅
	 */
	private BinarySearchTreeADTImpl<Integer> TC3 = null;

	/*
	 * 10 | 5 | | ∅ | | ∅ | 20 | | ∅ | | 30 | | | ∅ | | | ∅
	 */
	private BinarySearchTreeADTImpl<Integer> TEx = null;

	/*
	 * 10 | 5 | | ∅ | | 7 | | | 6 | | | | ∅ | | | | ∅ | | | ∅ | 15 | | ∅ | | ∅
	 * 
	 */
	private BinarySearchTreeADTImpl<Integer> TV1 = null;

	@Before
	public void setupBSTs() {

		TE = new BinarySearchTreeADTImpl<Integer>();

		T1234 = new BinarySearchTreeADTImpl<Integer>();
		T1234.insert(1, 2, 3, 4);
		Assert.assertEquals(T1234.toString(), "{1, ∅, {2, ∅, {3, ∅, {4, ∅, ∅}}}}");

		T4321 = new BinarySearchTreeADTImpl<Integer>();
		T4321.insert(4, 3, 2, 1);
		Assert.assertEquals(T4321.toString(), "{4, {3, {2, {1, ∅, ∅}, ∅}, ∅}, ∅}");

		TC3 = new BinarySearchTreeADTImpl<Integer>();
		TC3.insert(50, 20, 80, 10, 30, 70, 90);
		Assert.assertEquals(TC3.toString(), "{50, {20, {10, ∅, ∅}, {30, ∅, ∅}}, {80, {70, ∅, ∅}, {90, ∅, ∅}}}");

		TEx = new BinarySearchTreeADTImpl<Integer>();
		TEx.insert(10, 20, 30, 5);
		Assert.assertEquals(TEx.toString(), "{10, {5, ∅, ∅}, {20, ∅, {30, ∅, ∅}}}");

		TV1 = new BinarySearchTreeADTImpl<Integer>();
		TV1.insert(10, 5, 7, 6, 15);
		Assert.assertEquals(TV1.toString(), "{10, {5, ∅, {7, {6, ∅, ∅}, ∅}}, {15, ∅, ∅}}");

	}

	@Test
	public void testInsertElementsNulo() {

		T1234 = new BinarySearchTreeADTImpl<Integer>();
		T1234.insert(1, 2, null, 4);
		Assert.assertEquals(T1234.toString(), "∅");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertElementException() {

		TE.insert((Integer) null);
	}

	@Test
	public void testTagDescendTC4() {

		T1234 = new BinarySearchTreeADTImpl<Integer>();
		T1234.insert(1, 2, 3, 4);
		Assert.assertEquals(T1234.toString(), "{1, ∅, {2, ∅, {3, ∅, {4, ∅, ∅}}}}");

		List<String> lista = new LinkedList<String>();
		TC3.parentChildPairsTagDescend(lista);
		Assert.assertEquals(lista.toString(), "[(80, 90), (80, 70), (50, 80), (50, 20), (20, 30), (20, 10)]");
		TC3.filterTags("descend");
		Assert.assertEquals(
				"{50 [(descend, 4)], {20 [(descend, 6)], {10 [(descend, 7)], ∅, ∅}, {30 [(descend, 5)], ∅, ∅}}, {80 [(descend, 2)], {70 [(descend, 3)], ∅, ∅}, {90 [(descend, 1)], ∅, ∅}}}",
				TC3.toString());

	}

	@Test
	public void testInsertCollection() {

		Collection<Integer> coll = new ArrayList<Integer>();
		coll.add(1);
		coll.add(2);
		coll.add(3);
		coll.add(4);

		TE.insert(coll);
		assertEquals(TE.toString(), "{1, ∅, {2, ∅, {3, ∅, {4, ∅, ∅}}}}");

		coll = new ArrayList<Integer>();
		TE = new BinarySearchTreeADTImpl<Integer>();

		coll.add(10);
		coll.add(5);
		coll.add(7);
		coll.add(6);
		coll.add(15);

		TE.insert(coll);
		assertEquals(TE.toString(), "{10, {5, ∅, {7, {6, ∅, ∅}, ∅}}, {15, ∅, ∅}}");

	}

	@Test
	public void testInsertCollectionNulo() {

		Collection<Integer> coll = new ArrayList<Integer>();
		coll.add(1);
		coll.add(null);
		coll.add(3);
		coll.add(4);

		TE.insert(coll);
		assertEquals(TE.toString(), "∅");

	}

	@Test
	public void testWithdrowElem() {

		TEx = new BinarySearchTreeADTImpl<Integer>();
		TEx.insert(10, 20, 30, 5);
		Assert.assertEquals(TEx.toString(), "{10, {5, ∅, ∅}, {20, ∅, {30, ∅, ∅}}}");
		TEx.withdraw(30);
		Assert.assertEquals(TEx.toString(), "{10, {5, ∅, ∅}, {20, ∅, ∅}}");

		TV1 = new BinarySearchTreeADTImpl<Integer>();
		TV1.insert(10, 5, 7, 6, 15);
		Assert.assertEquals(TV1.toString(), "{10, {5, ∅, {7, {6, ∅, ∅}, ∅}}, {15, ∅, ∅}}");
		TV1.withdraw(6);
		Assert.assertEquals(TV1.toString(), "{10, {5, ∅, {7, ∅, ∅}}, {15, ∅, ∅}}");

		TC3 = new BinarySearchTreeADTImpl<Integer>();
		TC3.insert(50, 20, 80, 10, 30, 70, 60);
		Assert.assertEquals(TC3.toString(), "{50, {20, {10, ∅, ∅}, {30, ∅, ∅}}, {80, {70, {60, ∅, ∅}, ∅}, ∅}}");
		TC3.withdraw(80);
		Assert.assertEquals(TC3.toString(), "{50, {20, {10, ∅, ∅}, {30, ∅, ∅}}, {70, {60, ∅, ∅}, ∅}}");

		TC3 = new BinarySearchTreeADTImpl<Integer>();
		TC3.insert(50, 20, 70, 10, 30, 80, 90);
		Assert.assertEquals(TC3.toString(), "{50, {20, {10, ∅, ∅}, {30, ∅, ∅}}, {70, ∅, {80, ∅, {90, ∅, ∅}}}}");
		TC3.withdraw(70);
		Assert.assertEquals(TC3.toString(), "{50, {20, {10, ∅, ∅}, {30, ∅, ∅}}, {80, ∅, {90, ∅, ∅}}}");

		TV1 = new BinarySearchTreeADTImpl<Integer>();
		TV1.insert(10, 6, 7, 15, 4, 5);
		Assert.assertEquals(TV1.toString(), "{10, {6, {4, ∅, {5, ∅, ∅}}, {7, ∅, ∅}}, {15, ∅, ∅}}");
		TV1.withdraw(6);
		Assert.assertEquals(TV1.toString(), "{10, {5, {4, ∅, ∅}, {7, ∅, ∅}}, {15, ∅, ∅}}");

		TV1 = new BinarySearchTreeADTImpl<Integer>();
		TV1.insert(10, 6, 7, 15, 4, 5, 20, 12, 13);
		Assert.assertEquals(TV1.toString(),
				"{10, {6, {4, ∅, {5, ∅, ∅}}, {7, ∅, ∅}}, {15, {12, ∅, {13, ∅, ∅}}, {20, ∅, ∅}}}");
		TV1.withdraw(15);
		Assert.assertEquals(TV1.toString(), "{10, {6, {4, ∅, {5, ∅, ∅}}, {7, ∅, ∅}}, {13, {12, ∅, ∅}, {20, ∅, ∅}}}");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithdrowNull() {
		TV1 = new BinarySearchTreeADTImpl<Integer>();
		TV1.insert(10, 6, 7, 15, 4, 5, 20, 12, 13);

		TV1.withdraw((Integer) null);
	}

	@Test
	public void testWithdrowArray() {

		T1234 = new BinarySearchTreeADTImpl<Integer>();
		T1234.insert(1, 2, 3, 4);
		Assert.assertEquals(T1234.toString(), "{1, ∅, {2, ∅, {3, ∅, {4, ∅, ∅}}}}");
		T1234.withdraw(1, 2, 3, 4);
		Assert.assertEquals(T1234.toString(), "∅");

	}

	@Test
	public void testWithdrowArrayNull() {

		T1234 = new BinarySearchTreeADTImpl<Integer>();
		T1234.insert(1, 2, 3, 4);
		Assert.assertEquals(T1234.toString(), "{1, ∅, {2, ∅, {3, ∅, {4, ∅, ∅}}}}");
		T1234.withdraw(1, 2, null, 4);

	}

	@Test
	public void testWithdrowColl() {

		Collection<Integer> coll = new ArrayList<Integer>();
		coll.add(1);
		coll.add(2);
		coll.add(3);
		coll.add(4);

		TE.insert(coll);
		assertEquals(TE.toString(), "{1, ∅, {2, ∅, {3, ∅, {4, ∅, ∅}}}}");
		TE.withdraw(coll);
		Assert.assertEquals(TE.toString(), "∅");

	}

	@Test
	public void testWithdrowArrayColl() {

		Collection<Integer> coll = new ArrayList<Integer>();
		coll.add(1);
		coll.add(2);
		coll.add(3);
		coll.add(4);

		TE.insert(coll);
		assertEquals(TE.toString(), "{1, ∅, {2, ∅, {3, ∅, {4, ∅, ∅}}}}");
		Collection<Integer> coll2 = new ArrayList<Integer>();
		coll2.add(1);
		coll2.add(2);
		coll2.add(null);
		coll2.add(4);
		TE.withdraw(coll2);

	}

	@Test
	public void testgetSubtreeWithPath() {

		TV1 = new BinarySearchTreeADTImpl<Integer>();
		TV1.insert(10, 6, 7, 15, 4, 5, 20, 12, 13);
		Assert.assertEquals(TV1.toString(),
				"{10, {6, {4, ∅, {5, ∅, ∅}}, {7, ∅, ∅}}, {15, {12, ∅, {13, ∅, ∅}}, {20, ∅, ∅}}}");
		assertEquals("13", TV1.getSubtreeWithPath("101").content.toString());
	}

	@Test
	public void testIsPathIn() {

		TV1 = new BinarySearchTreeADTImpl<Integer>();
		TV1.insert(10, 6, 7, 15, 4, 5, 20, 12, 13);

		List<Integer> path = new ArrayList<Integer>();
		path.add(10);
		path.add(15);
		path.add(12);
		//{50 [(path, 1)], {30 [(path, 2)], {10 [(path, 3)], ∅, ∅}, {40, ∅, ∅}}, {80,
			// {60, ∅, ∅}, ∅}}
		assertEquals(true, TV1.isPathIn(path));
		Assert.assertEquals(TV1.toString(),
				"{10 [(path, 1)], {6, {4, ∅, {5, ∅, ∅}}, {7, ∅, ∅}}, {15 [(path, 2)], {12 [(path, 3)], ∅, {13, ∅, ∅}}, {20, ∅, ∅}}}");

		path = new ArrayList<Integer>();
		path.add(10);
		path.add(30);
		path.add(12);
		assertEquals(false, TV1.isPathIn(path));
		
		path = new ArrayList<Integer>();
		path.add(40);
		path.add(30);
		path.add(12);
		assertEquals(false, TV1.isPathIn(path));

	}

}
package ule.edi.tree;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class WorldTests {

	private World w = null;
	private World complete1= null;

	private String rutaComplete1="{[F(1)], {[F(1)], {[W(1)], {[D(1)], ∅, ∅}, {[D(3)], ∅, ∅}}, {[D(3)], {[P(4)], ∅, ∅}, {[P(3)], ∅, ∅}}}, {[F(1)], {[P(7)], {[P(1)], ∅, ∅}, {[P(2)], ∅, ∅}}, {[C(1), D(1)], {[P(1)], ∅, ∅}, {[P(1)], ∅, ∅}}}}";
	
	@Before
	public void setupWorlds() {
		
		w = World.createEmptyWorld();		
	}
	
	@Test
	public void testInsertCompleteTree() {
	
	rellenaArbolCompleto();

	Assert.assertEquals(rutaComplete1, complete1.toString());	
	}

	private void rellenaArbolCompleto() {
		complete1 = World.createEmptyWorld();
		complete1.insert("11", new Entity(Entity.DRAGON));
		complete1.insert("11", new Entity(Entity.CASTLE));
		complete1.insert("10", new Entity(Entity.PRINCESS,3));
		complete1.insert("10", new Entity(Entity.PRINCESS,4));
		complete1.insert("01", new Entity(Entity.DRAGON,3));
		complete1.insert("010", new Entity(Entity.PRINCESS,4));
		
		complete1.insert("00", new Entity(Entity.WARRIOR));
		complete1.insert("000", new Entity(Entity.DRAGON));
		complete1.insert("001", new Entity(Entity.DRAGON,3));
		complete1.insert("011", new Entity(Entity.PRINCESS,3));
		complete1.insert("100", new Entity(Entity.PRINCESS,1));
		complete1.insert("101", new Entity(Entity.PRINCESS,2));
		complete1.insert("110", new Entity(Entity.PRINCESS,1));
		complete1.insert("111", new Entity(Entity.PRINCESS,1));
	}

	@Test
	public void testInsertRootEmpty() {
		w.insert("", new Entity(Entity.DRAGON));
		Assert.assertEquals("{[D(1)], ∅, ∅}", w.toString());
	}
	
	@Test
	public void testInsertRootEmptyCardinality() {
		w.insert("", new Entity(Entity.DRAGON));
		w.insert("", new Entity(Entity.DRAGON));
		Assert.assertEquals("{[D(2)], ∅, ∅}", w.toString());
	}
	
	@Test
	public void testInsertRootEmptyEntitiesWithCardinality() {
		w.insert("", new Entity(Entity.DRAGON));
		w.insert("", new Entity(Entity.DRAGON));
		w.insert("", new Entity(Entity.CASTLE));
		Assert.assertEquals("{[C(1), D(2)], ∅, ∅}", w.toString());
	}

	
	@Test
	public void testInsertCreatesForest() {
		w.insert("0", new Entity(Entity.DRAGON));
		Assert.assertEquals("{[F(1)], {[D(1)], ∅, ∅}, ∅}", w.toString());
	}
	
	@Test
    public void testInsertCreatesForests() {
		w.insert("11", new Entity(Entity.DRAGON));
		Assert.assertEquals("{[F(1)], ∅, {[F(1)], ∅, {[D(1)], ∅, ∅}}}", w.toString());		
	}

	

	//	Contar entidades
	//
	
	@Test
	public void testCountOnEmpty() {
		Assert.assertTrue(w.isEmpty());
		Assert.assertEquals(0, w.countEntity(Entity.PRINCESS));
	}
	
	@Test
	public void testCountingEntitiesBasics() {
		w.insert("111", Entity.dragons(1));
		w.insert("1111", Entity.dragons(1));
		w.insert("111", Entity.castles(1));
		w.insert("100", Entity.dragons(1));
	
		Assert.assertEquals(1, w.countEntity(Entity.CASTLE));
		Assert.assertEquals(3, w.countEntity(Entity.DRAGON));
		Assert.assertEquals(4, w.countEntity(Entity.FOREST));
		Assert.assertEquals(0, w.countEntity(Entity.PRINCESS));
	}
	
	@Test
    public void testcountEntity() {
		w.insert("001", new Entity(Entity.DRAGON));
		w.insert("0010", new Entity(Entity.CASTLE));
		w.insert("10", new Entity(Entity.CASTLE));
		w.insert("001", new Entity(Entity.CASTLE));
		
		Assert.assertEquals("{[F(1)], {[F(1)], {[F(1)], ∅, {[C(1), D(1)], {[C(1)], ∅, ∅}, ∅}}, ∅}, {[F(1)], {[C(1)], ∅, ∅}, ∅}}", w.toString());
		Assert.assertEquals(4, w.countEntity(Entity.FOREST));
		Assert.assertEquals(3, w.countEntity(Entity.CASTLE));
		Assert.assertEquals(1, w.countEntity(Entity.DRAGON));
	}
	
	@Test
	public void testcountEntityInComplete() {
		this.rellenaArbolCompleto();		
		Assert.assertEquals(3, complete1.countEntity(Entity.FOREST));
		Assert.assertEquals(1, complete1.countEntity(Entity.CASTLE));
		Assert.assertEquals(8, complete1.countEntity(Entity.DRAGON));
		Assert.assertEquals(19, complete1.countEntity(Entity.PRINCESS));
	}
	

	 // tests de countAccesiblePrincesHeight	
	
	
	@Test
	public void testAccesiblePrincessWithoutDragon() {
		w.insert("11", Entity.princesses(10));
		w.insert("10", Entity.princesses(10));
		w.insert("01", Entity.princesses(10));
		w.insert("00", Entity.princesses(10));
		LinkedList<World> lista = new LinkedList<World>();
		Assert.assertEquals(w.toString(),"{[F(1)], {[F(1)], {[P(10)], ∅, ∅}, {[P(10)], ∅, ∅}}, {[F(1)], {[P(10)], ∅, ∅}, {[P(10)], ∅, ∅}}}");
		Assert.assertEquals(w.countAccesiblePrincesHeight(3,lista),40);
		Assert.assertEquals(lista.size(),4);
	}

	@Test
	public void testAccesiblePrincessWithDragonInRoot() {
		w.insert("11", Entity.princesses(10));
		w.insert("10", Entity.princesses(10));
		w.insert("01", Entity.princesses(10));
		w.insert("00", Entity.princesses(10));
		w.insert("", Entity.dragons(1));
		LinkedList<World> lista = null;
		Assert.assertEquals(w.countAccesiblePrincesHeight(2,lista),0);
	}
	

	
}

package it.polito.tdp.food.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		m.creaGrafo(100);
		List<String> vertici = m.getVertici();
		System.out.println(m.getCorrelate(vertici.get(2)));
	}

}

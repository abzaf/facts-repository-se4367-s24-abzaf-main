package facts;

import java.util.ArrayList;
import java.util.Random;

public class FactList {
	private ArrayList<Fact> factList;
	private Random randomGen;

	public FactList() {
		this.factList = new ArrayList<>();
		randomGen = new Random (System.currentTimeMillis());
	}

	public void set(Fact f) {
		factList.add(f);
	}

	public int getSize() {
		return factList.size();
	}

	public Fact get(int i) {
		return factList.get(i);
	}

	public ArrayList<Fact> getFactList() {
		return factList;
	}

	public void saveFactsToXML(String filePath) {
		try {
			FactListToXMLWriter writer = new FactListToXMLWriter(filePath);
			writer.writeFactList(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FactList search(String searchString, int mode) {
		FactList fl = new FactList();
		for (int i = 0; i < factList.size(); i++) {
			Fact fact = factList.get(i);
			if (mode == FactSearchMode.AUTHOR_VAL &&
					fact.getAuthor().toLowerCase().contains(searchString.toLowerCase())) {
				fl.set(fact);
			} else if (mode == FactSearchMode.TEXT_VAL &&
					fact.getText().toLowerCase().contains(searchString.toLowerCase())) {
				fl.set(fact);
			} else if (mode == FactSearchMode.TYPE_VAL &&
					fact.getType().toLowerCase().contains(searchString.toLowerCase())) {
				fl.set(fact);
			} else if ((mode == FactSearchMode.ALL_VAL) &&
					(fact.getAuthor().toLowerCase().contains(searchString.toLowerCase()) ||
							fact.getText().toLowerCase().contains(searchString.toLowerCase()) ||
							fact.getType().toLowerCase().contains(searchString.toLowerCase()))) {
				fl.set(fact);
			}
		}
		return fl;
	}

	public Fact getRandom(){
		return factList.get(randomGen.nextInt(factList.size()));
	}

}

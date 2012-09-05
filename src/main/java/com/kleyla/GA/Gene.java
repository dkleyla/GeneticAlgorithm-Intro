package com.kleyla.GA;

public class Gene {

	private String code = "";
	private int cost;

	public Gene() {
	}

	public Gene(String code) {
		this.code = code;
		setCost(9999);
	}

	public void random(int length) {
		while (length-- > 0) {
			Double randomVal = Math.floor(Math.random() * 255);
			this.code += new Character((char) randomVal.intValue());
		}
		System.out.println("Adding code " + this.code);
	}

	public void calcCost(String compareTo) {
		int total = 0;
		for (int i = 0; i < this.code.length(); i++) {
			total += ((this.code.charAt(i) - compareTo.charAt(i)) * (this.code.charAt(i) - compareTo.charAt(i)));
		}
		this.setCost(total);
	}

	public Gene[] mate(Gene gene) {
		int pivot = Math.round(this.code.length() / 2) - 1;
		String code1 = this.code.substring(0, pivot) + gene.code.substring(pivot);
		String code2 = gene.code.substring(0, pivot) + this.code.substring(pivot);

		Gene child1 = new Gene(code1);
		Gene child2 = new Gene(code2);
		return new Gene[] { child1, child2 };
	}

	public void mutate(double chance) {
		if (Math.random() > chance) {
			return;
		}
		Double index = Math.floor(Math.random() * this.code.length());
		double upOrDown = Math.random() <= 0.5 ? -1 : 1;
		char newChar = new Character((char) (this.code.charAt(index.intValue()) + upOrDown));
		String newString = "";
		for (int i = 0; i < this.code.length(); i++) {
			if (i == index) {
				newString += newChar;
			} else {
				newString += this.code.charAt(i);
			}
		}
		setCode(newString);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCost() {
		return cost;
	}
}
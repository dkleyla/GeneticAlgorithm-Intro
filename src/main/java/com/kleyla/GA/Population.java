package com.kleyla.GA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Population {

	private final String goal;
	private final List<Gene> members;
	private int generationNumber = 0;

	public Population(String goal, int size) {
		this.members = new ArrayList<Gene>();
		this.goal = goal;
		while (size-- > 0) {
			Gene gene = new Gene();
			gene.random(this.goal.length());
			this.members.add(gene);
		}
	}

	public List<Gene> sort() {
		Collections.sort(members, new Comparator<Gene>() {
			public int compare(Gene gene1, Gene gene2) {
				return gene1.getCost() - gene2.getCost();
			}
		});
		return members;
	}

	public void generation() {
		for (int i = 0; i < this.members.size(); i++) {
			this.members.get(i).calcCost(this.goal);
		}

		this.sort();
		this.display();
		Gene[] children = this.members.get(0).mate(this.members.get(1));
		splice(children);
		for (int i = 0; i < this.members.size(); i++) {

			this.members.get(i).mutate(0.5);
			this.members.get(i).calcCost(this.goal);
			if (this.members.get(i).getCode().equals(this.goal)) {
				this.sort();
				this.display();
				return;
			}
		}
		this.generationNumber++;
		try {
			Thread.sleep(50);
			generation();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void display() {
		System.out.println("Generation: " + this.generationNumber);
		for (int i = 0; i < this.members.size(); i++) {
			System.out.println("\t" + this.members.get(i).getCode() + " (" + this.members.get(i).getCost() + ")");
		}
	}

	private void splice(Gene[] children) {
		int insertPoint = this.members.size() - 2;
		this.members.remove(insertPoint);
		this.members.remove(insertPoint);
		this.members.add(insertPoint, children[0]);
		this.members.add(insertPoint + 1, children[1]);
	}
}

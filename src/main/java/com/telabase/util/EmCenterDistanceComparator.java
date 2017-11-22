package com.telabase.util;

import java.util.Comparator;

import com.telabase.ds.entity.EmCenter;

public class EmCenterDistanceComparator implements Comparator<EmCenter> {
	@Override
	public int compare(EmCenter a, EmCenter b) {
		return a.getDistance() < b.getDistance() ? -1 : a.getDistance() == b.getDistance() ? 0 : 1;
	}
}

package com.telabase.util;

import java.util.List;

import com.telabase.ds.entity.EmCenter;

public class SortUtil {

	public static SortUtil getInstance() {
		return new SortUtil();
	}
	

	public int[] sort(int[] array) {
		int num = array.length;
		int temp;
		for (int i = 0; i < (num - 1); i++) {
			for (int j = 0; j < num - i - 1; j++) {
				if (array[j] < array[j + 1]) {
					temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
		return array;
	}
}

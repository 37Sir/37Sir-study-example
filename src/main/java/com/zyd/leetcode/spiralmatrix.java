package com.zyd.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 螺旋矩阵
 * @author leshu
 *
 */
public class spiralmatrix {
    public List<Integer> spiralOrder(int[][] matrix) {
    		List<Integer> list = new ArrayList<Integer>();
    		while (matrix.length >=1) {
    			//拿出当前的第一行
    			for (int i =0; i<matrix[0].length; i++) {
    				list.add(matrix[0][i]);
    			}
    			if (matrix.length>1) {
    				//去掉第一行
    				matrix = Arrays.copyOfRange(matrix, 1, matrix.length);
    				//逆时针旋转
    				matrix = changeArrar(matrix);
    			} else {
    				break;
    			}
    		}
    		return list;
    }

	private int[][] changeArrar(int[][] matrix) {
        int[][] result = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int[] curRow = matrix[i];
            for (int j = 0; j < curRow.length; j++) {
                result[matrix[0].length - j - 1][i] = curRow[j];
            }
        }
        return result;
	}
}

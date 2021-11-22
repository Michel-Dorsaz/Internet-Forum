package com.example.Internet.Forum.domain;

import java.util.ArrayList;
import java.util.List;


public class ToolSet {
	
	public List filter(List<? extends Post> posts, String filter) {
		
		if(filter == null || filter.length() == 0) {
			return posts;
		}
	
		List<Post> matchingPosts = new ArrayList<Post>();
		
		for(Post p : posts) {
			
			int position = BoyerMooreHorspoolSearch(
					p.getContent().toLowerCase(), 
					filter.toLowerCase()
					);
			
			
			if(position != -1) {
				matchingPosts.add(p);
			}
			
		}
		
		return matchingPosts;
	}

	/**
	 * Implementation of the Boyer-Moore-Horspool search algorithm. Implementation
	 * not working perfectly, algorythm fixed by myself.
	 * 
	 * Code taken from
	 * http://www.mathcs.emory.edu/~cheung/Courses/253/Syllabus/Text/Matching-Boyer-Moore2.html
	 * 
	 * @author michel
	 * @author Boyer
	 * @author Moore
	 * @author Horspool
	 * 
	 *
	 */
	public int BoyerMooreHorspoolSearch(String T, String P) {
		int[] lastOcc;
		int i0, j, m, n;

		n = T.length();
		m = P.length();

		lastOcc = computeLastOcc(P); // Find last occurence of all characters in P

		i0 = 0; // Line P up at T[0]

		while (i0 <= (n - m)) {

			j = m - 1; // Start at the last char in P
			
			while (P.charAt(j) == T.charAt(i0 + j)) {

				j--; // Check "next" (= previous) character

				if (j < 0)
					return (i0); // P found !
			}

			/*
			 * ========================================================== The character in T
			 * aligned with P[m-1] is: T[i0+(m-1))] Always use character T[i0 + (m-1)] to
			 * find the shift ==========================================================
			 */
			// i0 = i0 + (m-1) - lastOcc[T.charAt(i0+(m-1))]; // Use last character: j =
			// (m-1)
			
			//Ensure only base unicode are checked
			if(T.charAt(i0+j) < 128) {
				i0 += lastOcc[T.charAt(i0+j)];		
			}
			
			i0++;

			
		}

		return -1; // no match
	}

	private int[] computeLastOcc(String P) {

		
		int[] lastOcc = new int[128]; // assume ASCII character set

		for (int i = 0; i < 128; i++) {
			lastOcc[i] = 0; // initialize all elements to 0
		}

		for (int i = 0; i < P.length(); i++) // Don't use the last char
												// to compute lastOcc[]
		{
			//Ensure only base unicode are checked
			if(P.charAt(i)<128)
				lastOcc[P.charAt(i)] = P.length()-i-2; // The LAST value will be store
		}

		lastOcc[P.charAt(P.length()-1)] = 0;
				
		return lastOcc;
	}
}

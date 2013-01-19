package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelpers {
	
	public static class StringPair {
		public final String first;
		public final String second;
		public StringPair(String first, String second) {
			super();
			this.first = first;
			this.second = second;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((first == null) ? 0 : first.hashCode());
			result = prime * result + ((second == null) ? 0 : second.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			StringPair other = (StringPair)obj;
			if (first == null) {
				if (other.first != null) return false;
			} else if (!first.equals(other.first)) return false;
			if (second == null) {
				if (other.second != null) return false;
			} else if (!second.equals(other.second)) return false;
			return true;
		}
		@Override
		public String toString() {
			return "StringPair [first=" + first + ", second=" + second + "]";
		}
	}
	
	public static class StringTriple {
		public final String first, second, third;

		public StringTriple(String first, String second, String third) {
			super();
			this.first = first;
			this.second = second;
			this.third = third;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((first == null) ? 0 : first.hashCode());
			result = prime * result + ((second == null) ? 0 : second.hashCode());
			result = prime * result + ((third == null) ? 0 : third.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			StringTriple other = (StringTriple)obj;
			if (first == null) {
				if (other.first != null) return false;
			} else if (!first.equals(other.first)) return false;
			if (second == null) {
				if (other.second != null) return false;
			} else if (!second.equals(other.second)) return false;
			if (third == null) {
				if (other.third != null) return false;
			} else if (!third.equals(other.third)) return false;
			return true;
		}

		@Override
		public String toString() {
			return "StringTriple [first=" + first + ", second=" + second + ", third=" + third + "]";
		}
	}

	public static List<String> batchMatchOneGroup(String regex, String input) throws Exception {
		if(!regex.contains("(") || !regex.contains(")"))
			throw new Exception("Regex must contain at least one group: " + regex);
		
		List<String> result = new LinkedList<String>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while(m.find()) {
			if(m.groupCount() == 1) {
				result.add(m.group(1));
			}
		}
		return result;
	}

	public static List<StringPair> batchMatchTwoGroups(String regex, String input) {
		List<StringPair> result = new LinkedList<StringPair>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while(m.find()) {
			if(m.groupCount() == 2) {
				result.add(new StringPair(m.group(1), m.group(2)));
			}
		}
		return result;
	}

	public static List<StringTriple> batchMatchThreeGroups(String regex, String input) {
		List<StringTriple> result = new LinkedList<StringTriple>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while(m.find()) {
			if(m.groupCount() == 3) {
				result.add(new StringTriple(m.group(1), m.group(2), m.group(3)));
			}
		}
		return result;
	}

}

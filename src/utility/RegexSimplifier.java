package utility;

import java.util.ArrayList;

import comportamental_fsm.labels.RelevantLabel;

public class RegexSimplifier {
	
	
	public String simplify(String regex) {
		return simplify(regex, new ArrayList<RelevantLabel>());
	}
	
	public String simplify(String regex, ArrayList<RelevantLabel> relLabels) {
		
		String simplification = regex;
		
		ArrayList<String> toSimplify = simplifyStrings(relLabels);
		
		while(toSimplify.stream().anyMatch(simplification::contains)) {
			simplification = simplification.replaceAll(String.format("\\(%s(\\|%<s)*\\)", Constants.EPSILON), Constants.EPSILON);
			simplification = simplification.replaceAll(String.format("%s+", Constants.EPSILON), Constants.EPSILON);
			simplification = simplification.replace(String.format("(%s(", Constants.EPSILON), "((");
			simplification = simplification.replace(String.format(")%s)", Constants.EPSILON), "))");
			simplification = simplification.replaceAll(String.format("^%s\\(", Constants.EPSILON), "(");
			simplification = simplification.replaceAll(String.format("\\)%s$", Constants.EPSILON), ")");			
			for(RelevantLabel relLabel: relLabels) {
				String rel = relLabel.content();
				simplification = simplification.replace(Constants.EPSILON+rel, rel);
				simplification = simplification.replace(rel+Constants.EPSILON, rel);
				simplification = simplification.replaceAll(String.format("\\(%s(\\|%<s)*\\)", rel), rel);
			}
		}
		
		return simplification;
	}
	
	private ArrayList<String> simplifyStrings(ArrayList<RelevantLabel> relLabels){
		ArrayList<String> toSimplify = new ArrayList<String>();
		toSimplify.add(String.format("(%s)", Constants.EPSILON));
		toSimplify.add(Constants.EPSILON+Constants.EPSILON);
		toSimplify.add(String.format("^%s\\(", Constants.EPSILON));
		toSimplify.add(String.format("\\)%s$", Constants.EPSILON));
		toSimplify.add(String.format("(%s(", Constants.EPSILON));
		toSimplify.add(String.format(")%s)", Constants.EPSILON));
		toSimplify.add(String.format("(%s|%<s)", Constants.EPSILON));
		for(RelevantLabel relLabel: relLabels) {
			String rel = relLabel.content();
			toSimplify.add(Constants.EPSILON+rel);
			toSimplify.add(rel+Constants.EPSILON);
			toSimplify.add(String.format("(%s)", rel));
			toSimplify.add(String.format("(%s|%<s)", rel));
		}
		
		return toSimplify;
	}

}

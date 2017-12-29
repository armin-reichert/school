package de.amr.schule.darts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DartsApp {

	private final int[][] intervals = { { 170, 150 }, { 149, 129 }, { 128, 108 }, { 107, 87 },
			{ 86, 66 }, { 65, 45 }, { 44, 24 }, { 23, 2 } };

	private final FinishesTable solutions;

	public DartsApp() {
		solutions = new FinishesTable();
	}

	public static void main(String[] args) {
		DartsApp app = new DartsApp();
		for (int[] interval : app.intervals) {
			int upper = interval[0], lower = interval[1];
			app.writeFile("finishes-" + upper + "-" + lower + ".htm", app.toHTML(upper, lower));
		}
		app.writeIndexFile("finishes-index.htm");
	}

	private int[] findInterval(int score) {
		for (int[] interval : intervals) {
			int upper = interval[0], lower = interval[1];
			if (upper >= score && score >= lower) {
				return interval;
			}
		}
		return null;
	}

	private void writeIndexFile(String path) {
		StringBuilder sb = new StringBuilder();

		sb.append("<!doctype html>\n");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<style>\n");
		sb.append(
				" .scorelink { text-align:right; font-family: Arial; font-size: 20pt; color: lightgray; });"
				+ " .scorelink a { text-decoration: none; }"
			  + " .scorelink a:visited { color: blue; }"
				+ "\n");
		sb.append("</style>\n");
		sb.append("</head>\n");
		sb.append("<body>\n");
		sb.append("<h1>Darts Finishes</h1>\n");
		sb.append("<table border=0 cellspacing=10>\n");

		// 2-dimensional table with scores
		int score = 170;
		int column = 1;
		sb.append("<tr>");
		while (score >= 2) {
			String url = "";
			int[] interval = findInterval(score);
			if (interval == null) {
				throw new IllegalStateException();
			}
			int upper = interval[0], lower = interval[1];
			boolean hasFinish = !solutions.getFinishes(score).isEmpty();
			url = "finishes-" + upper + "-" + lower + ".htm#" + +score;
			sb.append("<td class='scorelink'>");
			if (hasFinish) {
				sb.append("<a href='").append(url).append("'>");
			}
			sb.append(score);
			if (hasFinish) {
				sb.append("</a>");
			}
			sb.append("</td>\n");
			column += 1;
			if (column == 14) {
				sb.append("</tr>");
				sb.append("<tr>");
				column = 1;
			}
			score -= 1;
		}

		sb.append("</table>\n");
		sb.append("</body>\n");
		sb.append("</html>\n");

		writeFile(path, sb.toString());
	}

	private String toHTML(int upper, int lower) {
		StringBuilder sb = new StringBuilder();

		sb.append("<!doctype html>\n");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("</head>\n");
		sb.append("<body>\n");
		sb.append("<table border=1 cellpadding=3>\n");

		// Schleife für die Finishes
		for (int score = upper; score >= lower; score -= 1) {
			sb.append("<tr>\n");
			sb.append("<td valign='top'>").append("<a id='" + score + "'>\n").append(score)
					.append("</a></td>\n");
			sb.append("<td>\n");
			// Finishes zum score
			for (Finish finish : solutions.getFinishes(score)) {
				sb.append(finish).append("&nbsp;&nbsp;\n");
			}
			sb.append("</td>\n");
			sb.append("</tr>\n");
		}

		sb.append("</table>\n");
		sb.append("</body>\n");
		sb.append("</html>\n");

		return sb.toString();
	}

	private void writeFile(String path, String html) {
		try {
			Files.write(Paths.get(path), html.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

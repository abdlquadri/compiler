package vanhack.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Challenge {

  /**
   * Takes comma separated values and sorts it alphabetically
   *
   * @param csvData: String - Unsorted CSV
   * @return String - Sorted CSV
   */
  public static String sortCsvColumns(String csvData) {

    final Iterator<String> csvLines = Arrays.stream(csvData.split("\n", -1)).iterator();
    final String firstLine = csvLines.next();
    final Iterator<String> headersByWord = Arrays.stream(firstLine.split(",", -1)).iterator();
    final HashMap<String, Integer> headersIndexMap = mapHeadersToUnsortedIndex(headersByWord);
    final List<String> sortedHeaders = sortHeaders(headersIndexMap);
    final StringBuilder sortedCsv = appendSortedHeadersToCsv(sortedHeaders);
    csvLines.forEachRemaining(line -> {
      final ArrayList<String> strings = new ArrayList<>();
      Iterator<String> eachLineAsWord = Arrays.stream(line.split(",", -1)).iterator();
      eachLineAsWord.forEachRemaining(strings::add);

      for (int i = 0; i < sortedHeaders.size(); i++) {
        final Integer index = headersIndexMap.get(sortedHeaders.get(i));
        sortedCsv.append(strings.get(index));
        if (i != sortedHeaders.size() - 1) {
          sortedCsv.append(",");
        }
      }
      if (csvLines.hasNext()) {
        sortedCsv.append("\n");
      }
    });
    return sortedCsv.toString();
  }

  private static List<String> sortHeaders(HashMap<String, Integer> headersIndexMap) {
    return headersIndexMap.keySet().stream()
        .sorted(String::compareToIgnoreCase).collect(Collectors.toList());
  }

  private static HashMap<String, Integer> mapHeadersToUnsortedIndex(
      Iterator<String> headersByWord) {
    final HashMap<String, Integer> headersIndexMap = new HashMap<>();
    AtomicInteger columnIndex = new AtomicInteger(0);
    headersByWord
        .forEachRemaining(word -> headersIndexMap.put(word, columnIndex.getAndIncrement()));
    return headersIndexMap;
  }

  private static StringBuilder appendSortedHeadersToCsv(List<String> sortedHeaders) {
    final StringBuilder sortedCsv = new StringBuilder();
    for (int i = 0; i < sortedHeaders.size(); i++) {
      sortedCsv.append(sortedHeaders.get(i));
      if (i != sortedHeaders.size() - 1) {
        sortedCsv.append(",");
      }
    }
    sortedCsv.append("\n");
    return sortedCsv;
  }
}
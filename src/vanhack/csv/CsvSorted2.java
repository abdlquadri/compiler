package vanhack.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class CsvSorted2 {

  public static void main(String[] args) {
//    final String anObject = CsvSorted.sortCsvColumns(
//        "Beth,Charles,Danielle,Adam,Eric\n17945,10091,10088,3907,10132\n2,12,13,48,11");
//    System.out.println(
//        "Adam,Beth,Charles,Danielle,Eric\n3907,17945,10091,10088,10132\n48,2,12,13,11".equals(
//            anObject));
//    System.out.println(anObject);
//    System.out.println("f");


    final String anObject1 = Challenge.sortCsvColumns(
        "Active,country,email,First Name,id,Last Name,Notes,Suffix\n"
            + "false,Poland,jwilliamson0@sfgate.com,Judith,1,Williamson,augue vel accumsan tellus,\n"
            + "false,China,pkelly1@reference.com,Paul,2,Kelly,lacus at velit,\n"
            + "false,China,lpayne2@ftc.gov,Lillian,3,Payne,pede libero quis orci,\n"
            + "false,Jamaica,eberry3@qq.com,Eric,4,Berry,odio donec,\n"
            + "false,Democratic Republic of the Congo,mdunn4@studiopress.com,Marie,5,Dunn,metus aenean fermentum donec ut,\n"
            + "true,Philippines,hmontgomery5@fastcompany.com,Helen,6,Montgomery,rutrum ac lobortis vel,Sr\n"
            + "false,Yemen,jkim6@nhs.uk,Jose,7,Kim,nunc viverra,\n"
            + "false,Thailand,cmason7@flickr.com,Carol,8,Mason,tortor sollicitudin mi,\n"
            + "true,Nigeria,asanchez8@google.com.hk,Andrew,9,Sanchez,purus phasellus in felis donec,\n"
            + "true,,bfrazier9@youku.com,Brandon,10,Frazier,,\n"
            + "true,United States,msimmonsa@seesaa.net,Maria,11,Simmons,scelerisque mauris sit amet eros,\n"
            + "true,Russia,nrobinsonb@dmoz.org,Nancy,12,Robinson,vestibulum ac,\n"
            + "true,,tmedinac@oracle.com,Thomas,13,Medina,,Jr\n"
            + "false,Poland,ccarterd@reddit.com,Craig,14,Carter,justo lacinia eget,IV\n"
            + "false,Indonesia,jsanderse@imageshack.us,Joan,15,Sanders,in eleifend quam a odio,\n"
            + "true,China,ajonesf@free.fr,Amy,16,Jones,ut massa,III\n"
            + "true,Peru,kwoodg@angelfire.com,Kathy,17,Wood,ipsum praesent blandit lacinia,\n"
            + "false,Panama,crobertsonh@google.com.au,Christopher,18,Robertson,faucibus orci luctus et ultrices,\n"
            + "false,Argentina,charveyi@indiatimes.com,Cheryl,19,Harvey,proin eu mi nulla ac,\n"
            + "false,Estonia,iwelchj@wordpress.org,Irene,20,Welch,pede morbi porttitor,\n");
    System.out.println(
        "id,First Name,Last Name,Suffix,email,Active,country,Notes\n1,Judith,Williamson,,jwilliamson0@sfgate.com,false,Poland,augue vel accumsan tellus\n2,Paul,Kelly,,pkelly1@reference.com,false,China,lacus at velit\n3,Lillian,Payne,,lpayne2@ftc.gov,false,China,pede libero quis orci\n4,Eric,Berry,,eberry3@qq.com,false,Jamaica,odio donec\n5,Marie,Dunn,,mdunn4@studiopress.com,false,Democratic Republic of the Congo,metus aenean fermentum donec ut\n6,Helen,Montgomery,Sr,hmontgomery5@fastcompany.com,true,Philippines,rutrum ac lobortis vel\n7,Jose,Kim,,jkim6@nhs.uk,false,Yemen,nunc viverra\n8,Carol,Mason,,cmason7@flickr.com,false,Thailand,tortor sollicitudin mi\n9,Andrew,Sanchez,,asanchez8@google.com.hk,true,Nigeria,purus phasellus in felis donec\n10,Brandon,Frazier,,bfrazier9@youku.com,true,,\n11,Maria,Simmons,,msimmonsa@seesaa.net,true,United States,scelerisque mauris sit amet eros\n12,Nancy,Robinson,,nrobinsonb@dmoz.org,true,Russia,vestibulum ac\n13,Thomas,Medina,Jr,tmedinac@oracle.com,true,,\n14,Craig,Carter,IV,ccarterd@reddit.com,false,Poland,justo lacinia eget\n15,Joan,Sanders,,jsanderse@imageshack.us,false,Indonesia,in eleifend quam a odio\n16,Amy,Jones,III,ajonesf@free.fr,true,China,ut massa\n17,Kathy,Wood,,kwoodg@angelfire.com,true,Peru,ipsum praesent blandit lacinia\n18,Christopher,Robertson,,crobertsonh@google.com.au,false,Panama,faucibus orci luctus et ultrices\n19,Cheryl,Harvey,,charveyi@indiatimes.com,false,Argentina,proin eu mi nulla ac\n20,Irene,Welch,,iwelchj@wordpress.org,false,Estonia,pede morbi porttitor".equals(
            anObject1));
    System.out.println(anObject1);
    System.out.println("f");
  }

  public static String sortCsvColumns(String csv_data) {
    System.out.println(csv_data);
    final StringTokenizer csvLines = new StringTokenizer(csv_data, "\n");
    final String firstLine = csvLines.nextToken();
    final StringTokenizer headersByWord = new StringTokenizer(firstLine, ",");
    final HashMap<String, Integer> headersIndexMap = new HashMap<>();
    final int columnSize = headersByWord.countTokens();
    for (int i = 0; i < columnSize; i++) {
      headersIndexMap.put(headersByWord.nextToken(), i);
    }
    final StringBuilder sortedCsv = new StringBuilder();
    final List<String> sortedHeaders = headersIndexMap.keySet().stream().sorted()
        .collect(Collectors.toList());
    for (int i = 0; i < sortedHeaders.size(); i++) {
      sortedCsv.append(sortedHeaders.get(i));
      if (i != sortedHeaders.size() - 1) {
        sortedCsv.append(",");
      }
    }
    sortedCsv.append("\n");
    csvLines.asIterator().forEachRemaining(line -> {
      final ArrayList<String> strings = new ArrayList<>();
      final String eachLineAsWord = (String) line;
      final String[] split = eachLineAsWord.split(",");
      Arrays.stream(split).iterator().forEachRemaining(word -> {
        strings.add(word);
      });

      for (int i = 0; i < sortedHeaders.size(); i++) {
        final Integer index = headersIndexMap.get(sortedHeaders.get(i));
        sortedCsv.append(strings.get(index));
        if (i != sortedHeaders.size() - 1) {
          sortedCsv.append(",");
        }
      }
      if (csvLines.hasMoreTokens()) {
        sortedCsv.append("\n");
      }
    });
    return sortedCsv.toString();
  }
}

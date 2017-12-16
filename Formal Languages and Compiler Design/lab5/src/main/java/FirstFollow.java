import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

class FirstFollow {
    static HashSet<String> terminals = new HashSet<>();
    static HashSet<String> nonTerminals = new HashSet<>();
    static HashMap<String, ArrayList<String>> niceRules = new HashMap<>();
    static HashMap<String, HashSet<ArrayList<String>>> rules = new HashMap<>();
    static HashMap<String, HashSet<String>> firstSet = new HashMap<>();
    static HashMap<String, HashSet<String>> followSet = new HashMap<>();
    static HashMap<String, ArrayList<String>> parseTable = new HashMap<>();
    static String startSymbol;


    private static void readInput() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream("grammar.txt")));
            List<String> lines = reader.lines().collect(Collectors.toList());
            startSymbol = lines.get(0).trim().split("->")[0].trim();
            for (String line : lines) {
                String[] sides = line.trim().split("->");
                String terminal = sides[0].trim();
                if (!nonTerminals.contains(terminal)) {
                    nonTerminals.add(terminal);
                }
                String[] productions = sides[1].trim().split("\\|");
                HashSet<ArrayList<String>> productionsSet = new HashSet<>();
                for (int j = 0; j < productions.length; j++) {
                    String production = productions[j];
                    ArrayList<String> rule = new ArrayList<>();
                    String[] elements = production.trim().split("\\s+");
                    for (int i = 0; i < elements.length; i++) {
                        rule.add(elements[i]);
                        if (!terminals.contains(elements[i]) && !nonTerminals.contains(elements[i]) && (!Character.isUpperCase(elements[i].charAt(0)))) {
                            terminals.add(elements[i]);
                        }
                    }
                    productionsSet.add(rule);

                }
                rules.put(terminal, productionsSet);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void buildFirstSet() {
        for (String s : terminals) {
            HashSet<String> set = new HashSet<>();
            set.add(s);
            firstSet.put(s, set);
        }
        for (String s : nonTerminals) {
            if (!firstSet.containsKey(s)) {
                HashSet<String> first = firstOf(s);
                firstSet.put(s, first);
            }
        }
    }

    public static void buildFollowSet() {
        for (String s : nonTerminals) {
            if (!followSet.containsKey(s)) {
                HashSet<String> follow = followOf(s);
                followSet.put(s, follow);
            }
        }
    }

    public static void beautifyRules() {
        Iterator it = rules.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry) it.next();
            HashSet<ArrayList<String>> productions = (HashSet<ArrayList<String>>) pair.getValue();
            String key = (String) pair.getKey();
            for (ArrayList production : productions) {
                i++;
                niceRules.put(i + " " + key, production);
            }
        }
    }

    public static void buildTable() {
        Iterator it = niceRules.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String[] indexKeys = ((String) pair.getKey()).trim().split("\\s+");
            String key = indexKeys[1];
            ArrayList<String> production = (ArrayList<String>) pair.getValue();
            ArrayList<String> columns = new ArrayList<>();
            for (int i = 0; i < production.size(); i++) {
                HashSet<String> first = firstSet.get(production.get(i));

                for (String string : first) {
                    if (!string.equals("epsilon")) {
                        columns.add(string);
                    }
                }
                if (!first.contains("epsilon")) {
                    break;
                }
                if (i == production.size() - 1) {
                    HashSet<String> follow = followSet.get(key);
                    for (String string : follow) {
                        if (!string.equals("epsilon")) {
                            columns.add(string);
                        }
                    }
                }
            }
            for (String column : columns) {
                String tableKey = key + column;
                parseTable.put(tableKey, production);
            }
        }

    }

    static HashSet<String> firstOf(String s) {
        HashSet<String> result = new HashSet<>();
        if (terminals.contains(s)) {
            result.add(s);
            return result;
        }
        Iterator it = rules.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getKey().equals(s)) {
                HashSet<ArrayList<String>> production = (HashSet<ArrayList<String>>) pair.getValue();
                for (ArrayList<String> arrayList : production) {
                    for (String ss : arrayList) {
                        HashSet<String> result2 = firstOf(ss);
                        result.addAll(result2);
                        if (result2.contains("epsilon")) {
                            continue;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    static HashSet<String> followOf(String s) {
        HashSet<String> result = new HashSet<>();
        if (s.equals(startSymbol)) {
            result.add("$");
        }
        Iterator it = rules.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String left = (String) pair.getKey();
            HashSet<ArrayList<String>> production = (HashSet<ArrayList<String>>) pair.getValue();
            for (ArrayList<String> arrayList : production) {
                if (arrayList.contains(s)) {
                    int symbolIndex = arrayList.indexOf(s);
                    int followIndex = symbolIndex + 1;
                    while (true) {
                        if (followIndex == arrayList.size()) {
                            if (!left.equals(s)) {
                                result.addAll(followOf(left));
                            }
                            break;
                        }
                        String followSymbol = arrayList.get(followIndex);
                        HashSet<String> firstOfFollow = firstOf(followSymbol);
                        if (!firstOfFollow.contains("epsilon")) {
                            result.addAll(firstOfFollow);
                            break;
                        } else {
                            firstOfFollow.remove("epsilon");
                            result.addAll(firstOfFollow);
                            followIndex++;
                        }
                    }
                }

            }

        }
        return result;
    }

    private static void checkInputs() {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream("inputs.txt")));
            List<String> lines = reader.lines().collect(Collectors.toList());
            for (String line : lines) {
                String[] input = line.trim().split("\\s+");
                ArrayList<String> inputList = new ArrayList<>();
                for (int i = 0; i < input.length; i++) {
                    inputList.add(input[i]);
                }
                verifyInput(inputList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void verifyInput(ArrayList<String> inputTape) throws IOException {
        String originalInput = "";
        for (String s:inputTape){
            originalInput += s;
        }
        Writer writer = null;
        writer = new BufferedWriter(new FileWriter("outputs.txt",true));
        Stack<String> stack = new Stack<>();
        ArrayList<String> outPutTape = new ArrayList<>();
        stack.push("$");
        stack.push(startSymbol);
        while (true) {
            String a = inputTape.get(0);
            String A = stack.pop();
            if (a.equals(A)) {
                if (a.equals("$")) {
                    writer.write(originalInput + ": accepted"+"\n");
                    for (String s : outPutTape) {
                        writer.write(s+"\n");
                    }
                    System.out.println(originalInput + ": accepted"+"\n");
                    break;
                }
                inputTape.remove(0);
            } else {
                String key = A + a;
                if (parseTable.containsKey(key)) {
                    ArrayList<String> production = parseTable.get(key);
                    String toPush = "";
                    for (String s : production) {
                        toPush += s;
                    }
                    for (int i = production.size() - 1; i >= 0; i--) {
                        if (!production.get(i).equals("epsilon")) {
                            stack.push(production.get(i));
                        }
                    }
                    outPutTape.add(key + "->" + toPush);
                } else {
                    writer.write(originalInput + ": errorState on"+key+"\n");

                    break;
                }
            }
        }
        writer.close();



    }

    public static void main(String[] args) {
        readInput();
        buildFirstSet();
        buildFollowSet();
        beautifyRules();
        buildTable();
        checkInputs();
    }


}

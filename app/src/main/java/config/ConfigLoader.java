package config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.BonusSymbol;
import model.StandardSymbol;
import model.Symbol;
import model.WinCombination;

// This class is responsible for reading the configuration from a JSON file and parse it into a Config object
// It uses the Jackson library to parse JSON data into Java objects
// The Config object contains information about the game configuration, including symbols, probabilities, and win combinations


public class ConfigLoader {
    public static Config loadConfig(String FilePath) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode configRoot = mapper.readTree(new File(FilePath));
        
        int rows = configRoot.has("rows") ? configRoot.get("rows").asInt() : 0;
        int columns = configRoot.has("columns") ? configRoot.get("columns").asInt() : 0;
        Map<String, Symbol> symbols = parseSymbols(configRoot.get("symbols"));
        List<Map<String, Object>> standardSymbolProbabilities = parseStandardSymbolProbabilities(configRoot.get("probabilities").get("standard_symbols"));
        Map<String, Integer> bonusSymbolProbabilities = parseBonusSymbolProbabilities(configRoot.get("probabilities").get("bonus_symbols").get("symbols"));
        Map<String, WinCombination> winCombinations = parseWinCombinations(configRoot.get("win_combinations"));
        
        return new Config(columns, rows, symbols, standardSymbolProbabilities, bonusSymbolProbabilities, winCombinations);
    }

    private static Map<String, Symbol> parseSymbols(JsonNode symbolsNode) {
        Map<String, Symbol> parsedSymbols = new HashMap<>();
        symbolsNode.fields().forEachRemaining(entry -> {
            String name = entry.getKey();
            JsonNode symbolNode = entry.getValue();
            String type = symbolNode.get("type").asText();
            if ("standard".equals(type)) {
                double rewardMultiplier = symbolNode.get("reward_multiplier").asDouble();
                parsedSymbols.put(name, new StandardSymbol(name, rewardMultiplier));
            } else if ("bonus".equals(type)) {
                String impact = symbolNode.get("impact").asText();
                double rewardMultiplier = symbolNode.has("reward_multiplier") ? symbolNode.get("reward_multiplier").asDouble() : 1.0;
                int extra = symbolNode.has("extra") ? symbolNode.get("extra").asInt() : 0;
                parsedSymbols.put(name, new BonusSymbol(name, impact, rewardMultiplier, extra));
            }
        });
        return parsedSymbols;
    }

    private static List<Map<String, Object>> parseStandardSymbolProbabilities(JsonNode probabilitiesNode) {
        List<Map<String, Object>> parsedProbabilities = new ArrayList<>();
        if (probabilitiesNode != null && probabilitiesNode.isArray()) {
            for (JsonNode probNode : probabilitiesNode) {
                Map<String, Object> probMap = new HashMap<>();
                probMap.put("column", probNode.get("column").asInt());
                probMap.put("row", probNode.get("row").asInt());
                Map<String, Integer> symbolProbs = new HashMap<>();
                probNode.get("symbols").fields().forEachRemaining(entry -> {
                    symbolProbs.put(entry.getKey(), entry.getValue().asInt());
                });
                probMap.put("symbols", symbolProbs);
                parsedProbabilities.add(probMap);
            }
        }
        return parsedProbabilities;
    }

    private static Map<String, Integer> parseBonusSymbolProbabilities(JsonNode bonusSymbolsNode) {
        Map<String, Integer> parsedProbabilities = new HashMap<>();
        if (bonusSymbolsNode != null && bonusSymbolsNode.isObject()) {
            bonusSymbolsNode.fields().forEachRemaining(entry -> {
                parsedProbabilities.put(entry.getKey(), entry.getValue().asInt());
            });
        }
        return parsedProbabilities;
    }

    private static Map<String, WinCombination> parseWinCombinations(JsonNode winCombinationsNode) {
        Map<String, WinCombination> parsedWinCombinations = new HashMap<>();
        if (winCombinationsNode != null && winCombinationsNode.isObject()) {
            winCombinationsNode.fields().forEachRemaining(entry -> {
                String name = entry.getKey();
                JsonNode wcNode = entry.getValue();
                String when = wcNode.get("when").asText();
                double rewardMultiplier = wcNode.get("reward_multiplier").asDouble();
                String group = wcNode.get("group").asText();
                if ("same_symbols".equals(when)) {
                    int count = wcNode.get("count").asInt();
                    parsedWinCombinations.put(name, new WinCombination(name, when, rewardMultiplier, group, count, null));
                } else if ("linear_symbols".equals(when)) {
                    List<List<String>> coveredAreas = new ArrayList<>();
                    if (wcNode.has("covered_areas") && wcNode.get("covered_areas").isArray()) {
                        for (JsonNode areaNode : wcNode.get("covered_areas")) {
                            List<String> area = new ArrayList<>();
                            for (JsonNode cellNode : areaNode) {
                                area.add(cellNode.asText());
                            }
                            coveredAreas.add(area);
                        }
                    }
                    parsedWinCombinations.put(name, new WinCombination(name, when, rewardMultiplier, group, 0, coveredAreas));
                }
            });
        }
        return parsedWinCombinations;
    }   
}

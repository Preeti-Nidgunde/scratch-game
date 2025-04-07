package config;

import java.util.List;
import java.util.Map;

import model.Symbol;
import model.WinCombination;

public class Config {
    private final int rows;
    private final int columns;
    private final Map<String, Symbol> symbols;
    private final List<Map<String, Object>> standardSymbolProbabilities;
    private final Map<String, Integer> bonusSymbolProbabilities;
    private final Map<String, WinCombination> winCombinations;


    public Config(int columns, int rows, Map<String, Symbol> symbols, List<Map<String, Object>> standardSymbolProbabilities, Map<String, Integer> bonusSymbolProbabilities, Map<String, WinCombination> winCombinations) {
        this.columns = columns;
        this.rows = rows;
        this.symbols = symbols;
        this.standardSymbolProbabilities = standardSymbolProbabilities;
        this.bonusSymbolProbabilities = bonusSymbolProbabilities;
        this.winCombinations = winCombinations;
    }

    // Default to 3 if not specified
    public int getColumns() {
        return columns == 0 ? 3 : columns; 
    }

    // Default to 3 if not specified
    public int getRows() {
        return rows == 0 ? 3 : rows; 
    }

    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    public List<Map<String, Object>> getStandardSymbolProbabilities() {
        return standardSymbolProbabilities;
    }

    public Map<String, Integer> getBonusSymbolProbabilities() {
        return bonusSymbolProbabilities;
    }

    public Map<String, WinCombination> getWinCombinations() {
        return winCombinations;
    }

}

package scratchgame;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class GameResult {
    //@JsonSerialize(using = MatrixGenerator.MatrixSerializer.class)
    private String[][] matrix;
    private final double reward;

    @JsonProperty("applied_winning_combinations")
    private final Map<String, List<String>> appliedWinningCombinations;

    @JsonProperty("applied_bonus_symbol")
    private final List<String> appliedBonusSymbols;

    public GameResult(String[][] matrix, double reward, Map<String, List<String>> appliedWinningCombinations, List<String> appliedBonusSymbols) {
        this.matrix = matrix;
        this.reward = reward;
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.appliedBonusSymbols = appliedBonusSymbols;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public double getReward() {
        return reward;
    }

    public Map<String, List<String>> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }

    public List<String> getAppliedBonusSymbols() {
        return appliedBonusSymbols;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

//     @Override
//     public String toString() {
//     StringBuilder sb = new StringBuilder();
//     sb.append("{\n");
    
//     // Matrix
//     sb.append("    \"matrix\": [\n");
//     for (int i = 0; i < matrix.length; i++) {
//         sb.append("        [\"")
//           .append(String.join("\", \"", matrix[i]))
//           .append("\"]");
//         if (i < matrix.length - 1) sb.append(",");
//         sb.append("\n");
//     }
//     sb.append("    ],\n");
    
//     // Reward
//     sb.append("    \"reward\": ")
//       .append(reward == (int)reward ? (int)reward : reward)
//       .append(",\n");
    
//     // Winning combinations
//     sb.append("    \"applied_winning_combinations\": {\n");
//     // ... add combinations formatting ...
//     sb.append("    },\n");
    
//     // Bonus symbol
//     sb.append("    \"applied_bonus_symbol\": \"")
//       .append(getAppliedBonusSymbols())
//       .append("\"\n");
    
//     sb.append("}");
//     return sb.toString();
// }
    
}
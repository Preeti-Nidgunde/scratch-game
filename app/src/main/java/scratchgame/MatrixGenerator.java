package scratchgame;

// import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import config.Config;

public class MatrixGenerator {
    private final Config config;
    private final Random random = new Random();

    public MatrixGenerator(Config config) {
        this.config = config;
    }

    public String[][] generateMatrix() {
        int rows = config.getRows();
        int columns = config.getColumns();
        String[][] matrix = new String[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Map<String, Integer> standardProbs = getStandardSymbolProbabilitiesForCell(j, i);
                Map<String, Integer> bonusProbs = config.getBonusSymbolProbabilities();
                Map<String, Integer> combinedProbs = new HashMap<>();
                String symbol;

                if (standardProbs != null) {
                    combinedProbs.putAll(standardProbs);
                }
                if (bonusProbs != null) {
                    combinedProbs.putAll(bonusProbs);
                }

                if (combinedProbs.isEmpty()) {
                    symbol = config.getSymbols().keySet().iterator().next(); // Fallback
                } else {
                    symbol = getRandomSymbolFromProbabilities(combinedProbs);
                }
                matrix[i][j] = symbol;
            }
        }

        return matrix;
    }

    private Map<String, Integer> getStandardSymbolProbabilitiesForCell(int column, int row) {
        for (Map<String, Object> probEntry : config.getStandardSymbolProbabilities()) {
            if ((int) probEntry.get("column") == column && (int) probEntry.get("row") == row) {
                return (Map<String, Integer>) probEntry.get("symbols");
            }
        }
        return null;
    }

    private String getRandomSymbolFromProbabilities(Map<String, Integer> probabilities) {
        int totalProbability = probabilities.values().stream().mapToInt(Integer::intValue).sum();
        if (totalProbability <= 0) {
            return null;
        }
        int randomNumber = random.nextInt(totalProbability);
        int cumulativeProbability = 0;
        for (Map.Entry<String, Integer> entry : probabilities.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (randomNumber < cumulativeProbability) {
                return entry.getKey();
            }
        }
        return null;
    }

    // // Custom serializer for the matrix to add newlines after each row
    // public static class MatrixSerializer extends StdSerializer<String[][]> {
    //     public MatrixSerializer() {
    //         this(null);
    //     }

    //     public MatrixSerializer(Class<String[][]> t) {
    //         super(t);
    //     }

    //     @Override
    //     public void serialize(String[][] matrix, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    //         jgen.writeStartArray();  // Start the matrix array

    //         for (String[] row : matrix) {
    //             jgen.writeStartArray();  // Start the row
    //             for (String cell : row) {
    //                 jgen.writeString(cell);  // Write each cell
    //             }
    //             jgen.writeEndArray();  // End the row
    //         }

    //         jgen.writeEndArray();  // End the matrix array
    //     }
    // }
}
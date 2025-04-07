/*
 * This source file was generated by the Gradle 'init' task
 */
package scratchgame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.Gson;

import config.Config;
import model.BonusSymbol;
import model.StandardSymbol;
import model.Symbol;
import model.WinCombination;

@ExtendWith(MockitoExtension.class)
class AppTest {
    private App game;
    @Mock
    private Config config;
    @Mock
    private MatrixGenerator matrixGenerator;
    private Gson gson = new Gson();

    @BeforeEach
    void setUp() {
        game = new App();
        game.setConfig(config);
        game.setMatrixGenerator(matrixGenerator);

        // Setup standard symbols
        Map<String, Symbol> symbols = new HashMap<>();
        symbols.put("A", new StandardSymbol("A", 50));
        symbols.put("B", new StandardSymbol("B", 25));
        symbols.put("C", new StandardSymbol("C", 10));
        symbols.put("D", new StandardSymbol("D", 5));
        symbols.put("E", new StandardSymbol("E", 3));
        symbols.put("F", new StandardSymbol("F", 1.5));
        symbols.put("10x", new BonusSymbol("10x", "multiply_reward", 10, 0));
        symbols.put("+1000", new BonusSymbol("+1000", "extra_bonus", 0, 1000));
        symbols.put("+500", new BonusSymbol("+500", "extra_bonus", 0, 500));
        symbols.put("5x", new BonusSymbol("5x", "multiply_reward", 5, 0));
        symbols.put("MISS", new BonusSymbol("MISS", "miss", 0, 0));
        when(config.getSymbols()).thenReturn(symbols);

        // Create win combinations
        Map<String, WinCombination> winCombinations = new HashMap<>();

        // Same symbol win combinations
        winCombinations.put("same_symbol_3_times",
                new WinCombination("same_symbol_3_times", "same_symbols", 1.0, "same_symbols", 3, null));
        winCombinations.put("same_symbol_4_times",
                new WinCombination("same_symbol_4_times", "same_symbols", 1.5, "same_symbols", 4, null));
        winCombinations.put("same_symbol_5_times",
                new WinCombination("same_symbol_5_times", "same_symbols", 2.0, "same_symbols", 5, null));

        // Linear horizontal combinations
        winCombinations.put("same_symbols_horizontally_row_0",
                new WinCombination("same_symbols_horizontally_row_0", "linear_symbols", 2.0, "horizontally_linear_symbols", 3,
                        Arrays.asList(Arrays.asList("0:0", "0:1", "0:2"))));
        winCombinations.put("same_symbols_horizontally_row_1",
                new WinCombination("same_symbols_horizontally_row_1", "linear_symbols", 2.0, "horizontally_linear_symbols", 3,
                        Arrays.asList(Arrays.asList("1:0", "1:1", "1:2"))));
        winCombinations.put("same_symbols_horizontally_row_2",
                new WinCombination("same_symbols_horizontally_row_2", "linear_symbols", 2.0, "horizontally_linear_symbols", 3,
                        Arrays.asList(Arrays.asList("2:0", "2:1", "2:2"))));

        // Linear vertical combinations
        winCombinations.put("same_symbols_vertically_col_0",
                new WinCombination("same_symbols_vertically_col_0", "linear_symbols", 2.0, "vertically_linear_symbols", 3,
                        Arrays.asList(Arrays.asList("0:0", "1:0", "2:0"))));
        winCombinations.put("same_symbols_vertically_col_1",
                new WinCombination("same_symbols_vertically_col_1", "linear_symbols", 2.0, "vertically_linear_symbols", 3,
                        Arrays.asList(Arrays.asList("0:1", "1:1", "2:1"))));
        winCombinations.put("same_symbols_vertically_col_2",
                new WinCombination("same_symbols_vertically_col_2", "linear_symbols", 2.0, "vertically_linear_symbols", 3,
                        Arrays.asList(Arrays.asList("0:2", "1:2", "2:2"))));

        // Diagonal combinations
        winCombinations.put("same_symbols_diagonally_left_to_right",
        new WinCombination("same_symbols_diagonally_left_to_right", "linear_symbols", 5.0, "ltr_diagonally_linear_symbols", 3,
                Arrays.asList(Arrays.asList("0:0", "1:1", "2:2"))));
        winCombinations.put("same_symbols_diagonally_right_to_left",
        new WinCombination("same_symbols_diagonally_right_to_left", "linear_symbols", 5.0, "rtl_diagonally_linear_symbols", 3,
                Arrays.asList(Arrays.asList("0:2", "1:1", "2:0"))));

        when(config.getWinCombinations()).thenReturn(winCombinations);
    }

    @Test
    void testHorizontalWin() {
        String[][] matrix = {
                {"F", "+1000", "+1000"},
                {"MISS", "+500", "MISS"},
                {"E", "E", "E"}
        };
        when(matrixGenerator.generateMatrix()).thenReturn(matrix);

        GameResult result = game.play(100);
        System.out.println("Result (Horizontal): " + gson.toJson(result));

        assertEquals(1, result.getAppliedWinningCombinations().size());
        assertTrue(result.getAppliedWinningCombinations().containsKey("E"));
        assertTrue(result.getAppliedWinningCombinations().get("E").contains("same_symbols_horizontally_row_2"));
        assertTrue(result.getAppliedWinningCombinations().get("E").contains("same_symbol_3_times"));
        assertEquals(3100.0, result.getReward(), 0.01); 
        assertTrue(result.getAppliedBonusSymbols().contains("+1000"));
        assertTrue(result.getAppliedBonusSymbols().contains("+500"));
    }

    @Test
    void testVerticalWin() {
        String[][] matrix = {
                {"A", "B", "C"},
                {"A", "+500", "F"},
                {"A", "D", "E"}
        };
        when(matrixGenerator.generateMatrix()).thenReturn(matrix);

        GameResult result = game.play(100);
        System.out.println("Result (Vertical): " + gson.toJson(result));

        assertEquals(1, result.getAppliedWinningCombinations().size());
        assertTrue(result.getAppliedWinningCombinations().containsKey("A"));
        assertTrue(result.getAppliedWinningCombinations().get("A").contains("same_symbols_vertically_col_0"));
        assertTrue(result.getAppliedWinningCombinations().get("A").contains("same_symbol_3_times"));
        assertTrue(result.getAppliedBonusSymbols().contains("+500"));
        assertEquals((100 * 50 * 2.0 * 1.0) + 500, result.getReward(), 0.01); // Vertical + Same Symbol + Bonus
    }

    @Test
    void testDiagonalLeftToRightWin() {
        String[][] matrix = {
                {"C", "B", "A"},
                {"F", "C", "E"},
                {"A", "D", "C"}
        };
        when(matrixGenerator.generateMatrix()).thenReturn(matrix);

        GameResult result = game.play(100);
        System.out.println("Result (Diagonal LTR): " + gson.toJson(result));

        assertEquals(1, result.getAppliedWinningCombinations().size());
        assertTrue(result.getAppliedWinningCombinations().containsKey("C"));
        assertTrue(result.getAppliedWinningCombinations().get("C").contains("same_symbols_diagonally_left_to_right"));
        assertTrue(result.getAppliedWinningCombinations().get("C").contains("same_symbol_3_times"));
        assertEquals((100 * 10 * 5.0* 1.0), result.getReward(), 0.01); // Diagonal LTR + Same Symbol
    }

    @Test
    void testDiagonalRightToLeftWin() {
        String[][] matrix = {
                {"A", "B", "A"},
                {"F", "A", "E"},
                {"A", "D", "C"}
        };
        when(matrixGenerator.generateMatrix()).thenReturn(matrix);

        GameResult result = game.play(100);
        System.out.println("Result (Diagonal RTL): " + gson.toJson(result));

        assertEquals(1, result.getAppliedWinningCombinations().size());
        assertTrue(result.getAppliedWinningCombinations().containsKey("A"));
        assertTrue(result.getAppliedWinningCombinations().get("A").contains("same_symbols_diagonally_right_to_left"));
        assertTrue(result.getAppliedWinningCombinations().get("A").contains("same_symbol_4_times"));
        assertEquals((100 * 50 * 5.0 * 1.5), result.getReward(), 0.01); // Diagonal RTL + Same Symbol
    }
    
    @Test
    void testMultipleHorizontalWins() {
    String[][] matrix = {
            {"A", "A", "A"},
            {"MISS", "+500", "MISS"},
            {"E", "E", "E"}
    };
    when(matrixGenerator.generateMatrix()).thenReturn(matrix);

    GameResult result = game.play(100);
    System.out.println("Result (Multiple Horizontal): " + gson.toJson(result));

    assertEquals(2, result.getAppliedWinningCombinations().size());
    assertTrue(result.getAppliedWinningCombinations().containsKey("A"));
    assertTrue(result.getAppliedWinningCombinations().containsKey("E"));
    assertTrue(result.getAppliedBonusSymbols().contains("+500"));
    assertFalse(result.getAppliedBonusSymbols().contains("MISS")); // Account for both MISS symbols
    assertEquals(11100.0, result.getReward(), 0.01);
}

    @Test
    void testMultipleVerticalWins() {
        String[][] matrix = {
                {"A", "B", "E"},
                {"A", "+500", "E"},
                {"A", "D", "E"}
        };
        when(matrixGenerator.generateMatrix()).thenReturn(matrix);

        GameResult result = game.play(100);
        System.out.println("Result (Multiple Vertical): " + gson.toJson(result));

        assertEquals(2, result.getAppliedWinningCombinations().size());
        assertTrue(result.getAppliedWinningCombinations().containsKey("A"));
        assertTrue(result.getAppliedWinningCombinations().containsKey("E"));
        
        assertTrue(result.getAppliedBonusSymbols().contains("+500"));
        assertEquals((100 * 50 * 2.0 * 1.0) + (100 * 3 * 2.0 * 1.0) + 500, result.getReward(), 0.01);
    }

    @Test
    void testCombinedWins() {
        String[][] matrix = {
                {"A", "A", "A"},
                {"F", "A", "E"},
                {"A", "E", "E"}
        };
        when(matrixGenerator.generateMatrix()).thenReturn(matrix);

        GameResult result = game.play(100);
        System.out.println("Result (Combined Wins): " + gson.toJson(result));

        assertEquals(2, result.getAppliedWinningCombinations().size());
        assertTrue(result.getAppliedWinningCombinations().containsKey("A"));
        assertTrue(result.getAppliedWinningCombinations().containsKey("E"));
        assertEquals(100300.0, result.getReward(), 0.01);
    }

    @Test
    void testMultipleDiagonalWins() {
        String[][] matrix = {
                {"A", "B", "E"},
                {"F", "A", "E"},
                {"E", "D", "A"}
        };
        when(matrixGenerator.generateMatrix()).thenReturn(matrix);

        GameResult result = game.play(100);
        System.out.println("Result (Multiple Diagonal): " + gson.toJson(result));

        assertEquals(2, result.getAppliedWinningCombinations().size());
        assertTrue(result.getAppliedWinningCombinations().containsKey("A"));
        assertTrue(result.getAppliedWinningCombinations().containsKey("E"));
        assertEquals((100 * 50 * 5.0 * 1.0) + (100 * 3 * 1.0), result.getReward(), 0.01);
    }

    @Test
    void testNoWins() {
        String[][] matrix = {
                {"A", "C", "A"},
                {"D", "C", "10x"},
                {"B", "D", "E"}
        };
        when(matrixGenerator.generateMatrix()).thenReturn(matrix);

        GameResult result = game.play(100);
        System.out.println("Result (Combined Wins): " + gson.toJson(result));

        assertEquals(0, result.getAppliedWinningCombinations().size());
        assertEquals(0.0, result.getReward(), 0.01);
    }
}
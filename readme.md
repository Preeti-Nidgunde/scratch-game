


To run this application, download the JAR from the release section 
Execute the below command to run the application:

```bash
 java -jar <your-jar-file> --config <config.json> --betting-amount <100>
```


To execute the test cases, use the below command:
```
./gradlew test
```



Designed a scratch game that will generate a matrix (for example 3x3) from symbols(based on probabilities for each cell), and based on winning combinations user either will win or lose.
The user will place a bet with any amount, which we call *betting amount* in this assignment.


There are two types of symbols: Standard Symbols, Bonus Symbols.

**Standard Symbols**: identifies if user won or lost the game based on winning combinations (combination can be X times repeated symbols or symbols that follow a specific pattern)
Bonus symbols are described the table below:


| Symbol Name | Reward Multiplier |
|-------------|-------------------|
| A           | 50                |
| B           | 25                |
| C           | 10                |
| D           | 5                 |
| E           | 3                 |
| F           | 1.5               |


**Bonus Symbols**: Bonus symbols are only effective when there are at least one winning combinations matches with the generated matrix. 
Bonus symbols are described the table below:

| Symbol Name | Action                       |
|-------------|------------------------------|
| 10x         | mutiply final reward to 10   |
| 5x          | mutiply final reward to 5    |
| +1000       | add 1000 to the final reward |
| +500        | add 500 to the final reward  |
| MISS        | none                         |


Let's look at the configuration file below:

```json
{
    "columns": 3, // OPTIONAL
    "rows": 3, // OPTIONAL
    "symbols": {
        "A": {
            "reward_multiplier": 50,
            "type": "standard"               
        },
        "B": {
            "reward_multiplier": 25,
            "type": "standard"               
        },
        "C": {
            "reward_multiplier": 10,
            "type": "standard"               
        },
        "D": {
            "reward_multiplier": 5,
            "type": "standard"               
        },
        "E": {
            "reward_multiplier": 3,
            "type": "standard"               
        },
        "F": {
            "reward_multiplier": 1.5,
            "type": "standard"               
        },

        "10x": {
            "reward_multiplier": 10,
            "type": "bonus",
            "impact": "multiply_reward"
        },
        "5x": {
            "reward_multiplier": 5,
            "type": "bonus",
            "impact": "multiply_reward"
        },
        "+1000": {
            "extra": 1000,
            "type": "bonus",
            "impact": "extra_bonus"
        },
        "+500": {
            "extra": 500,
            "type": "bonus",
            "impact": "extra_bonus"
        },
        "MISS": {
            "type": "bonus",
            "impact": "miss"
        }
    },
    "probabilities": {
        "standard_symbols": [
            {
            "column": 0,
            "row": 0,
            "symbols": {
                "A": 1,
                "B": 2,
                "C": 3,
                "D": 4,
                "E": 5,
                "F": 6
            }
            },
            {
            "column": 0,
            "row": 1,
            "symbols": {
                "A": 1,
                "B": 2,
                "C": 3,
                "D": 4,
                "E": 5,
                "F": 6
            }
            }
            //...
        ],
        "bonus_symbols": {
            "symbols": {
                "10x": 1,
                "5x": 2,
                "+1000": 3,
                "+500": 4,
                "MISS": 5
            }
        }
    },
    "win_combinations": {
        "same_symbol_3_times": {
            "reward_multiplier": 1,
            "when": "same_symbols",
            "count": 3,
            "group": "same_symbols"
        },
        "same_symbol_4_times": {
            "reward_multiplier": 1.5,
            "when": "same_symbols",
            "count": 4,
            "group": "same_symbols"
        },
        "same_symbol_5_times": {
            "reward_multiplier": 2,
            "when": "same_symbols",
            "count": 5,
            "group": "same_symbols"
        },
        "same_symbol_6_times": {
            "reward_multiplier": 3,
            "when": "same_symbols",
            "count": 6,
            "group": "same_symbols"
        },
        "same_symbol_7_times": {
            "reward_multiplier": 5,
            "when": "same_symbols",
            "count": 7,
            "group": "same_symbols"
        },
        "same_symbol_8_times": {
            "reward_multiplier": 10,
            "when": "same_symbols",
            "count": 8,
            "group": "same_symbols"
        },
        "same_symbol_9_times": {
            "reward_multiplier": 20,
            "when": "same_symbols",
            "count": 9,
            "group": "same_symbols"
        },

        "same_symbols_horizontally": { // OPTIONAL
            "reward_multiplier": 2,
            "when": "linear_symbols",
            "group": "horizontally_linear_symbols",
            "covered_areas": [
                ["0:0", "0:1", "0:2"],
                ["1:0", "1:1", "1:1"],
                ["2:0", "2:1", "2:2"]
            ]
        },
        "same_symbols_vertically": { // OPTIONAL
            "reward_multiplier": 2,
            "when": "linear_symbols",
            "group": "vertically_linear_symbols",
            "covered_areas": [
                ["0:0", "1:0", "2:0"],
                ["0:1", "1:1", "2:1"],
                ["0:2", "1:2", "2:2"]
            ]
        },
        "same_symbols_diagonally_left_to_right": { // OPTIONAL
            "reward_multiplier": 5,
            "when": "linear_symbols",
            "group": "ltr_diagonally_linear_symbols",
            "covered_areas": [
                ["0:0", "1:1", "2:2"]
            ]
        },
        "same_symbols_diagonally_right_to_left": { // OPTIONAL
            "reward_multiplier": 5,
            "when": "linear_symbols",
            "group": "rtl_diagonally_linear_symbols",
            "covered_areas": [
                ["0:2", "1:1", "2:0"]
            ]
        }
    }
}
```

Input format:

```
    "bet_amount": 100
```

| field name | description    |
|------------|----------------|
| bet_amount | betting amount |

Output format:

```json
{
    "matrix": [
        ["A", "A", "B"],
        ["A", "+1000", "B"],
        ["A", "A", "B"]
    ],
    "reward": 6600,
    "applied_winning_combinations": {
        "A": ["same_symbol_5_times", "same_symbols_vertically"],
        "B": ["same_symbol_3_times", "same_symbols_vertically"]
    },
    "applied_bonus_symbol": "+1000"
}
```

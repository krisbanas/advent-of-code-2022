INPUT = "../resources/Day2Input.txt"

pointsOne = {
    'A X': 4,  # rock, rock
    'A Y': 8,  # rock, paper
    'A Z': 3,  # rock, sci
    'B X': 1,  # paper, rock
    'B Y': 5,  # paper, paper
    'B Z': 9,  # paper, sci
    'C X': 7,  # sci, rock
    'C Y': 2,  # sci, paper
    'C Z': 6,  # sci, sci
}

count = 0

with open(INPUT) as f:
    for line in f:
        entry = line.strip()
        count += pointsOne[entry]

print("Part1: ", count)

pointsTwo = {
    'A X': 3,  # rock, rock
    'A Y': 4,  # rock, paper
    'A Z': 8,  # rock, sci
    'B X': 1,  # paper, rock
    'B Y': 5,  # paper, paper
    'B Z': 9,  # paper, sci
    'C X': 2,  # sci, rock
    'C Y': 6,  # sci, paper
    'C Z': 7,  # sci, sci
}

count = 0

with open(INPUT) as f:
    for line in f:
        entry = line.strip()
        count += pointsTwo[entry]

print("Part2: ", count)

def priority(c):
    lower_case_prio = ord(str.lower(c)) - ord('a') + 1
    if str.lower(c):
        return lower_case_prio
    else:
        return lower_case_prio + 26


INPUT = "../resources/Day3Input.txt"

with open(INPUT) as f:
    lines = f.readlines()

characters = []
result = 0

for line in lines:
    midpoint = len(line) // 2
    left, right = line[:midpoint], line[midpoint:]
    for char in list(left):
        if char in right:
            result += priority(char)
            break

print("Part one: ", result)

i = 0
characters = []

while i < len(lines):
    for char in lines[i]:
        if char in lines[i + 1] and char in lines[i + 2]:
            characters.append(char)
            break
    i += 3

print(characters)
result = 0

for char in characters:
    result += ord(str.lower(char)) - ord('a') + 1
    if str.isupper(char):
        result += 26

print("Part two: ", result)

def priority(c):
    lower_case_prio = ord(str.lower(c)) - ord('a') + 1
    if str.islower(c):
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

result = 0
while i < len(lines):
    for char in lines[i]:
        if char in lines[i + 1] and char in lines[i + 2]:
            result += priority(char)
            break
    i += 3

print("Part two: ", result)

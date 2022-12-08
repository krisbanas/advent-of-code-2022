import re


def ints(string):
    return list(map(int, re.findall(r"-?[0-9]+", string)))


INPUT = "../resources/Day6Input.txt"

with open(INPUT) as f:
    lines = "".join(f.readlines())

for index in range(len(lines) - 1):
    four = lines[index:index + 4]
    if len(set(four)) == len(four):
        print("Part 1:", index + 4)
        break

for index in range(len(lines) - 1):
    four = lines[index:index + 14]
    if len(set(four)) == len(four):
        print("Part 2:", index + 14)
        break

import re
`

def ints(string):
    return list(map(int, re.findall(r"-?[0-9]+", string)))


INPUT = "../resources/Day4Input.txt"

with open(INPUT) as f:
    lines = f.readlines()

count1 = 0
count2 = 0

for line in lines:
    one, two = line.split(",")
    startOne, endOne = [int(x) for x in one.split("-")]
    startTwo, endTwo = [int(x) for x in two.split("-")]
    if (startOne <= startTwo and endTwo <= endOne) or (startTwo <= startOne and endOne <= endTwo):
        count1 += 1
    if (endTwo >= startOne >= startTwo) or (endOne >= startTwo >= startOne):
        count2 += 1

print("Part 1: ", count1)
print("Part 2: ", count2)

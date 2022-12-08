import re


def ints(string):
    return list(map(int, re.findall(r"-?[0-9]+", string)))


INPUT = "../resources/Day5Input.txt"

with open(INPUT) as f:
    lines = f.readlines()

stacks1 = [[] for _ in range(10)]

for line in lines[:8]:
    for i, c in enumerate(line[1::4]):  # like a foreach with iterator
        if not c.isspace():
            stacks1[i + 1].insert(0, c)  # inserts object in front

stacks2 = [s[:] for s in stacks1]

for line in lines[10:]:
    tmp = []
    count, source, target = ints(line)

    # Part 1
    for _ in range(count):
        stacks1[target].append(stacks1[source].pop())

    # Part 2
    for _ in range(count):
        tmp.append(stacks2[source].pop())
    for _ in range(count):
        stacks2[target].append(tmp.pop())

result1 = "".join(s[-1] for s in stacks1[1:])
result2 = "".join(s[-1] for s in stacks2[1:])

print("Part 1:", result1)
print("Part 2:", result2)

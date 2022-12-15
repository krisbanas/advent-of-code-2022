from collections import defaultdict

INPUT = "../resources/Day7Input.txt"

lines = [x.strip() for x in open(INPUT)]

wd = []
SZ = defaultdict(int)

for line in lines:
    words = line.strip().split()
    print(wd)
    if words[1] == 'cd':
        if words[2] == '..':
            wd.pop()
        else:
            wd.append(words[2])
    elif words[0] == '$' or words[0] == 'dir':
        continue
    else:
        size = int(words[0])
        for i in range(len(wd) + 1):
            SZ['/'.join(wd[:i])] += size
print(SZ)

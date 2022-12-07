INPUT = "../resources/Day4Input.txt"

with open(INPUT) as f:
    lines = f.readlines()

count = 0

for line in lines:
    (left, right) = line.split(",")
    (lStart, lEnd) = left.split("-")
    (rStart, rEnd) = right.split("-")
    if (lStart <= rStart and lEnd >= rEnd) or (lStart >= rStart and lEnd <= rEnd):
        count += 1
    else:
        print(lStart, "-", lEnd, "-", rStart, "-", rEnd)

print("Part 1: ", count)

count = 0

with open(INPUT) as f:
    lines = f.readlines()

for line in lines:
    (left, right) = line.split(",")
    (lStart, lEnd) = left.split("-")
    (rStart, rEnd) = right.split("-")
    if (rEnd >= lStart and rStart <= lStart) or (lEnd >= rStart and lStart <= rStart):
        count += 1
    else:
        print(lStart, "-", lEnd, "-", rStart, "-", rEnd)

print("Part 1: ", count)

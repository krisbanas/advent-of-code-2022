X = [l.strip() for l in open("../resources/Day01Input.txt")]

X = ('\n'.join(X)).split('\n\n')
result = []
for line in X:
    q = 0
    for el in line.split('\n'):
        q += int(el)
    result.append(q)
result = sorted(result)
result = result[-3:]

print(result)
print("part one: ", result[-1])
print("part two: ", sum(result))

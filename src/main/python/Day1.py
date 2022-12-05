INPUT = "../resources/Day1Input.txt"

X = [line.strip() for line in open(INPUT)]

X = ('\n'.join(X)).split('\n\n')
result = []
for elf in X:
    q = 0
    for el in elf.split('\n'):
        q += int(el)
    result.append(q)
result = sorted(result)
result = result[-3:]

print(result)
print("Part one: ", result[-1])
print("Part two: ", sum(result))

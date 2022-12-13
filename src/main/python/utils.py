import re


def ints(string):
    return list(map(int, re.findall(r"-?[0-9]+", string)))

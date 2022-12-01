#!/usr/bin/env python3

import argparse
import datetime
import json
import sys

import requests

today = datetime.date.today()

parser = argparse.ArgumentParser(description='Process some integers.')
parser.add_argument('--day', default=today.day, help='day to get input for')
parser.add_argument('--year', default=today.year, help='year to get input for')

args = parser.parse_args()

with open('input-loader.json') as f:
    config_data = json.load(f)

r = requests.get(f"https://adventofcode.com/{args.year}/day/{args.day}/input",
                 cookies={"session": config_data["session"]},
                 headers={"User-Agent": "github.com/krisbanas/advent-of-code-2022 by krzysztof.banasik@protonmail.com"}
                 )

sys.stdout.write(r.text + "\n")
with open(f"../resources/Day{args.day}Input.txt", "w") as f:
    f.write(r.text)

sys.stderr.write("OK\n")

package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileReader;

import static java.lang.System.out;

public class Day04 {

    public Day04() {
        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        var input = FileReader.readAsStringList("Day4Input.txt");
        int result = 0;
        for (String section : input) {
            Sections sections = createSections(section);
            Section f = sections.first();
            Section s = sections.second();
            if ((f.start() <= s.start() && f.end() >= s.end()) || (s.start() <= f.start() && s.end() >= f.end()))
                result++;
        }
        return result;
    }

    private Object part2() {
        var input = FileReader.readAsStringList("Day4Input.txt");
        int result = 0;
        for (String section : input) {
            Sections sections = createSections(section);
            Section f = sections.first();
            Section s = sections.second();
            if ((f.end() >= s.start() && f.start() <= s.start()) || (s.end() >= f.start() && s.start() <= f.start()))
                result++;
        }
        return result;
    }

    private static Sections createSections(String section) {
        String firstElf = section.split(",")[0];
        String secondElf = section.split(",")[1];
        Section firstSection = new Section(Integer.parseInt(firstElf.split("-")[0]), Integer.parseInt(firstElf.split("-")[1]));
        Section secondSection = new Section(Integer.parseInt(secondElf.split("-")[0]), Integer.parseInt(secondElf.split("-")[1]));
        return new Sections(firstSection, secondSection);
    }

    private record Section(int start, int end) {}

    private record Sections(Section first, Section second) {}
}

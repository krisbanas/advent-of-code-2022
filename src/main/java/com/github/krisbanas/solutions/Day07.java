package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileHelper;

import java.util.*;

import static java.lang.System.out;

public class Day07 {
    public Day07() {
        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        File root = readInput();

        long counter = findSmallDirsSize(root);
        return counter;
    }

    private Object part2() {
        File root = readInput();

        List<File> directories;
        directories = findDirectories(root);
        directories.sort(Comparator.comparingLong(a -> a.size()[0]));

        long missingPlace = Math.abs(70000000 - root.size()[0] - 30000000);
        for (File dir : directories) {
            if (dir.size()[0] >= missingPlace) return dir.size()[0];
        }
        return null;
    }

    private long findDirsSize(File file) {
        if (file.files().size() == 0) return 0;
        long result = file.size()[0];
        result += file.files().stream()
                .mapToLong(this::findDirsSize)
                .sum();
        return result;
    }

    private List<File> findDirectories(File file) {
        if (file.files().isEmpty()) return Collections.emptyList();
        List<File> result = new ArrayList<>();
        result.add(file);
        file.files().forEach(f -> result.addAll(findDirectories(f)));
        return result;
    }


    private long findSmallDirsSize(File file) {
        if (file.files().size() == 0) return 0;
        long result = file.size()[0] <= 100000 ? file.size()[0] : 0;
        result += file.files().stream()
                .mapToLong(this::findSmallDirsSize)
                .sum();
        return result;
    }

    private static File readInput() {
        var xd = FileHelper.loadString("Day7Input.txt");
        var split = xd.split("\\$");
        File root = null;
        File pwd = null;

        for (String entry : split) {
            if (entry.isBlank()) continue;
            List<String> lines = new ArrayList<>(Arrays.stream(entry.split("\n")).toList());
            var command = getCommand(lines);
            if (command.type().equals("cd")) {
                if (command.parameter().equals("/")) {
                    root = new File("/", null, new ArrayList<>(), new long[1]);
                    pwd = root;
                } else if (command.parameter().equals("..")) {
                    if (pwd == root) continue;
                    pwd = pwd.parent();
                } else {
                    pwd = pwd.files().stream().filter(x -> x.name().equals(command.parameter())).findFirst().get();
                }
            } else if (command.type().equals("ls")) {
                lines.remove(0);
                for (String fileLine : lines) {
                    String[] fileLineSplit = fileLine.trim().split(" ");
                    File newDir;
                    if (fileLineSplit[0].equals("dir")) {
                        newDir = new File(fileLineSplit[1], pwd, new ArrayList<>(), new long[1]);
                    } else {
                        newDir = new File(fileLineSplit[1], pwd, new ArrayList<>(), new long[]{Long.parseLong(fileLineSplit[0])});
                    }
                    pwd.files().add(newDir);
                }
            }
        }
        root.size()[0] = countSize(root);
        return root;
    }

    private static long countSize(File file) {
        if (file.files().size() == 0) return file.size()[0];
        file.size()[0] = file.files().stream()
                .mapToLong(Day07::countSize)
                .sum();
        return file.size()[0];
    }

    private static Command getCommand(List<String> lines) {
        var cmdArray = Arrays.stream(lines.get(0).split(" "))
                .map(String::strip)
                .filter(x -> !x.isBlank())
                .toArray(String[]::new);
        if (cmdArray.length < 2) return new Command(cmdArray[0], ".");
        return new Command(cmdArray[0], cmdArray[1]);
    }

    record File(String name, File parent, List<File> files, long[] size) {
        @Override
        public String toString() {
            return name;
        }
    }

    record Command(String type, String parameter) {
    }
}

package com.github.krisbanas.solutions;

import com.github.krisbanas.util.FileReader;

import java.util.*;

import static java.lang.System.out;

public class Day07 {
    public Day07() {
        out.println(part1());
        out.println(part2());
    }

    public Object part1() {
        File root = readInput();
        return findSmallDirsSize(root);
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

    private static File readInput() {
        var xd = FileReader.readAsString("Day7Input.txt");
        var split = xd.split("\\$ ");
        File root = new File("/", null, new ArrayList<>(), new long[1]);
        File pwd = root;

        for (String entry : split) {
            if (entry.isBlank() || entry.equals("cd /\n")) continue;
            List<String> lines = new ArrayList<>(Arrays.stream(entry.split("\n")).toList());
            var command = getCommand(lines);
            if (command.type().equals("cd")) {
                pwd = command.parameter().equals("..")
                        ? pwd.parent()
                        : pwd.files().stream().filter(x -> x.name().equals(command.parameter())).findFirst().get();
            } else {
                lines.remove(0);
                for (String fileLine : lines) {
                    String[] fileLineSplit = fileLine.trim().split(" ");
                    long[] size = fileLineSplit[0].equals("dir") ? new long[1] : new long[]{Long.parseLong(fileLineSplit[0])};
                    File newDir = new File(fileLineSplit[1], pwd, new ArrayList<>(), size);
                    pwd.files().add(newDir);
                }
            }
        }
        root.size()[0] = countSize(root);
        return root;
    }

    private long findSmallDirsSize(File file) {
        if (file.files().size() == 0) return 0;
        long result = file.size()[0] <= 100000 ? file.size()[0] : 0;
        result += file.files().stream()
                .mapToLong(this::findSmallDirsSize)
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

    private static long countSize(File file) {
        return file.size()[0] = file.files().stream()
                .mapToLong(Day07::countSize)
                .sum() + file.size()[0];
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

        @Override
        public File parent() {
            return "/".equals(name) ? this : parent;
        }

        public boolean isFile() {
            return files.isEmpty();
        }
    }

    record Command(String type, String parameter) {
    }
}

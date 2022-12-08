
fun main() {

    class File(val name: String, val size: Int)
    class Directory(val name: String) {
        var parent: Directory? = null
        val directories: MutableList<Directory> = mutableListOf()
        val files: MutableList<File> = mutableListOf()

        val size: Int
            get() = files.sumOf { it.size } + directories.sumOf { it.size }

        fun getAllDirectories(dirs: List<Directory>): List<Directory> {
            if (directories.isEmpty()) {
                return (dirs + listOf(this)).distinctBy { it.name }
            }

            return (directories + directories.flatMap { it.getAllDirectories(it.directories) }).distinctBy { it.name }
        }

        val root: Directory
            get() {
                if (parent == null) { return this }
                return parent!!.root
            }

        fun addDirectory(child: Directory): Directory {
            child.parent = this
            directories.add(child)

            return child
        }

        fun addFile(file: File) {
            files.add(file)
        }

        fun setFile(name: String, value: Int) {
            val index = files.indexOfFirst { it.name == name }

            if (index != -1) {
                files[index] = File(name, value)
            } else {
                directories.forEach { it.setFile(name, value) }
            }
        }

        fun containsDir(key: String): Boolean {
            if (directories.isEmpty()) {
                return key == name
            }

            return directories.any {
                it.containsDir(key)
            }
        }

        fun containsFile(key: String): Boolean {
            if (directories.isEmpty()) {
                return files.any { it.name == key }
            }

            return directories.any {
                files.any { file -> file.name == key } || it.containsFile(key)
            }
        }

        fun findChild(key: String): Directory? {
            if (directories.any { it.name == key }) {
                return directories.firstOrNull { it.name == key }
            }

            return directories.firstOrNull { it.findChild(key) != null }
        }

        override fun toString(): String {
            return printTree(0)
        }

        private fun printTree(level: Int): String {
            val indent = (0..level).joinToString("") {"  "}
            var result = "$indent-> $name (dir)"
            directories.forEach {
                result += "\n" + it.printTree(level + 1)
            }
            if (files.isNotEmpty()) {
                result += "  $indent${files.joinToString("") { "\n  $indent-> ${it.name} = ${it.size}" }}"
            }
            return result
        }
    }

    fun part1(input: List<String>): Int {
        var location = Directory("/")

        var lineIndex = 0
        while (lineIndex < input.size) {
            println("$lineIndex: processing " + input[lineIndex])
            val pieces = input[lineIndex].split(" ")
            if (input[lineIndex].startsWith("$")) {
                when (pieces[1]) {
                    "cd" -> {
                        val argument = pieces[2]
                        location = if (argument == "..") {
                            location.parent ?: location.root
                        } else {
                            if (argument == "/") {
                                location.root
                            } else if (!location.containsDir(argument)) {
                                location.addDirectory(Directory(argument))
                            } else {
                                location.findChild(argument) ?: location.root
                            }
                        }
                    }
                    "ls" -> {
                        lineIndex++

                        val sublist = input.subList(lineIndex, input.size)
                        var endIndex = sublist.indexOfFirst { it.startsWith("$") }
                        if (endIndex == - 1) { endIndex = sublist.size }

                        for (i in 0 until endIndex) {
                            println("${lineIndex + i}: adding to file structure: ${sublist[i]}")
                            val (dirOrSize, name) = sublist[i].split(" ")
                            if (dirOrSize == "dir") {
                                if (!location.root.containsDir(name)) {
                                    println("adding directory $name to ${location.name}")
                                    location.addDirectory(Directory(name))
                                } else {
                                    println("using directory $name")
                                    location.findChild(name)
                                }
                            } else if (!location.containsFile(name)) {
                                println("adding file $name to ${location.name}")
                                location.addFile(File(name, dirOrSize.toInt()))
                            } else {
                                println("updating file $name within ${location.name}")
                                location.setFile(name, dirOrSize.toInt())
                            }

                        }
                        lineIndex += endIndex - 1
                    }
                }
            }

            lineIndex++
        }

        println("---------- Current Tree --------------")
        println(location.root.toString())

        val max = 100000


        val countedDirs = location.root.getAllDirectories(emptyList()).filter {
            println("directory ${it.name} = ${it.size}")
            it.size <= max
        }.distinctBy { it.name }
        println("result = ${countedDirs.sumOf { it.size }}")

        println(location.root.getAllDirectories(emptyList()).joinToString { it.name })
        return countedDirs.sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
//    check(part2(testInput) == 19)

    val input = readInput("Day07")
    println("Part 1:")
    println(part1(input))
//    println("Part 2:")
//    println(part2(input))
}

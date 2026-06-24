package phonebook

import java.io.File
import kotlin.math.sqrt

fun main() {
    val directoryFile = File("directory.txt")
    val findFile = File("find.txt")

    if (!directoryFile.exists() || !findFile.exists()) {
        return
    }

    val directory = directoryFile.readLines()
    val find = findFile.readLines()

    // ---------------- LINEAR SEARCH ----------------
    println("Start searching (linear search)...")
    val linearStart = System.currentTimeMillis()
    var foundLinear = 0
    for (name in find) {
        for (entry in directory) {
            if (entry.contains(name)) {
                foundLinear++
                break
            }
        }
    }
    val linearEnd = System.currentTimeMillis()
    val linearTime = linearEnd - linearStart
    println("Found $foundLinear / ${find.size} entries. Time taken: ${formatTime(linearTime)}")
    println()

    // ---------------- BUBBLE SORT + JUMP SEARCH ----------------
    println("Start searching (bubble sort + jump search)...")
    val bubbleSortStart = System.currentTimeMillis()
    val bubbleSortedDirectory = directory.toMutableList()
    var bubbleStopped = false

    bubbleSortLoop@ for (i in 0 until bubbleSortedDirectory.size - 1) {
        var swapped = false
        for (j in 0 until bubbleSortedDirectory.size - i - 1) {
            if (bubbleSortedDirectory[j].substringAfter(" ") > bubbleSortedDirectory[j + 1].substringAfter(" ")) {
                val temp = bubbleSortedDirectory[j]
                bubbleSortedDirectory[j] = bubbleSortedDirectory[j + 1]
                bubbleSortedDirectory[j + 1] = temp
                swapped = true
            }
        }
        if (!swapped) break
        if (System.currentTimeMillis() - bubbleSortStart > linearTime * 10) {
            bubbleStopped = true
            break@bubbleSortLoop
        }
    }

    val bubbleSortEnd = System.currentTimeMillis()
    val bubbleSortTime = bubbleSortEnd - bubbleSortStart
    val jumpSearchStart = System.currentTimeMillis()
    var foundJump = 0

    if (bubbleStopped) {
        // Fallback to linear search
        for (name in find) {
            for (entry in directory) {
                if (entry.contains(name)) {
                    foundJump++
                    break
                }
            }
        }
    } else {
        for (name in find) {
            if (jumpSearch(bubbleSortedDirectory, name)) {
                foundJump++
            }
        }
    }

    val jumpSearchEnd = System.currentTimeMillis()
    val jumpSearchTime = jumpSearchEnd - jumpSearchStart

    println("Found $foundJump / ${find.size} entries. Time taken: ${formatTime(bubbleSortTime + jumpSearchTime)}")
    print("Sorting time: ${formatTime(bubbleSortTime)}")
    if (bubbleStopped) println(" - STOPPED, moved to linear search") else println()
    println("Searching time: ${formatTime(jumpSearchTime)}")
    println()

    // ---------------- QUICK SORT + BINARY SEARCH ----------------
    println("Start searching (quick sort + binary search)...")
    val quickSortStart = System.currentTimeMillis()
    val quickSortedDirectory = directory.toMutableList()
    
    quickSort(quickSortedDirectory, 0, quickSortedDirectory.size - 1)
    
    val quickSortEnd = System.currentTimeMillis()
    val quickSortTime = quickSortEnd - quickSortStart
    val binarySearchStart = System.currentTimeMillis()
    var foundBinary = 0

    for (name in find) {
        if (binarySearch(quickSortedDirectory, name)) {
            foundBinary++
        }
    }

    val binarySearchEnd = System.currentTimeMillis()
    val binarySearchTime = binarySearchEnd - binarySearchStart

    println("Found $foundBinary / ${find.size} entries. Time taken: ${formatTime(quickSortTime + binarySearchTime)}")
    println("Sorting time: ${formatTime(quickSortTime)}")
    println("Searching time: ${formatTime(binarySearchTime)}")
    println()

    // ---------------- HASH TABLE ----------------
    println("Start searching (hash table)...")
    val hashTableStart = System.currentTimeMillis()
    val hashTable = mutableMapOf<String, String>()
    for (entry in directory) {
        val name = entry.substringAfter(" ")
        val number = entry.substringBefore(" ")
        hashTable[name] = number
    }
    val hashTableEnd = System.currentTimeMillis()
    val creationTime = hashTableEnd - hashTableStart

    val hashSearchStart = System.currentTimeMillis()
    var foundHash = 0
    for (name in find) {
        if (hashTable.containsKey(name)) {
            foundHash++
        }
    }
    val hashSearchEnd = System.currentTimeMillis()
    val hashSearchTime = hashSearchEnd - hashSearchStart

    println("Found $foundHash / ${find.size} entries. Time taken: ${formatTime(creationTime + hashSearchTime)}")
    println("Creating time: ${formatTime(creationTime)}")
    println("Searching time: ${formatTime(hashSearchTime)}")
}

fun formatTime(ms: Long): String {
    val minutes = ms / 60000
    val seconds = (ms % 60000) / 1000
    val millis = ms % 1000
    return "$minutes min. $seconds sec. $millis ms."
}

fun jumpSearch(list: List<String>, target: String): Boolean {
    val n = list.size
    if (n == 0) return false
    val step = sqrt(n.toDouble()).toInt()
    var prev = 0
    var curr = step

    while (curr < n && list[curr - 1].substringAfter(" ") < target) {
        prev = curr
        curr += step
    }

    for (i in prev until minOf(curr, n)) {
        if (list[i].contains(target)) return true
    }
    return false
}

fun quickSort(list: MutableList<String>, left: Int, right: Int) {
    if (left < right) {
        val pivotIndex = partition(list, left, right)
        quickSort(list, left, pivotIndex - 1)
        quickSort(list, pivotIndex + 1, right)
    }
}

fun partition(list: MutableList<String>, left: Int, right: Int): Int {
    val pivotIndex = (left..right).random()
    val tempPivot = list[pivotIndex]
    list[pivotIndex] = list[right]
    list[right] = tempPivot

    val pivotValue = list[right].substringAfter(" ")
    var i = left - 1
    for (j in left until right) {
        if (list[j].substringAfter(" ") <= pivotValue) {
            i++
            val temp = list[i]
            list[i] = list[j]
            list[j] = temp
        }
    }
    val temp = list[i + 1]
    list[i + 1] = list[right]
    list[right] = temp
    return i + 1
}

fun binarySearch(list: List<String>, target: String): Boolean {
    var low = 0
    var high = list.size - 1
    while (low <= high) {
        val mid = low + (high - low) / 2
        val name = list[mid].substringAfter(" ")
        if (list[mid].contains(target)) return true
        if (name < target) {
            low = mid + 1
        } else {
            high = mid - 1
        }
    }
    return false
}

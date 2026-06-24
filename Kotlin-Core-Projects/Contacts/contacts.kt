package contacts

import java.io.*
import java.time.LocalDateTime

abstract class Record : Serializable {
    protected var phoneNumber = ""
    val createdTime: LocalDateTime = LocalDateTime.now()
    var editedTime: LocalDateTime = createdTime

    fun setNumber(number: String) {
        if (isValidNumber(number)) {
            phoneNumber = number
        } else {
            println("Wrong number format!")
            phoneNumber = ""
        }
        editedTime = LocalDateTime.now()
    }

    fun getNumber(): String =
        if (phoneNumber.isBlank()) "[no number]" else phoneNumber

    private fun isValidNumber(number: String): Boolean {
        val regex = Regex(
            """^\+?(\([A-Za-z0-9]+\)|[A-Za-z0-9]+)([ -](\([A-Za-z0-9]{2,}\)|[A-Za-z0-9]{2,}))*$"""
        )
        return regex.matches(number)
    }

    abstract fun shortInfo(): String
    abstract fun fullInfo()
    abstract fun getFields(): List<String>
    abstract fun getField(field: String): String
    abstract fun setField(field: String, value: String)
    abstract fun getAllValues(): String
}

class Person(
    var name: String,
    var surname: String,
    birth: String,
    gender: String,
    number: String
) : Record() {

    var birthDate = if (birth.isBlank()) {
        println("Bad birth date!")
        "[no data]"
    } else birth

    var genderValue = if (gender != "M" && gender != "F") {
        println("Bad gender!")
        "[no data]"
    } else gender

    init {
        setNumber(number)
    }

    override fun shortInfo(): String = "$name $surname"

    override fun fullInfo() {
        println("Name: $name")
        println("Surname: $surname")
        println("Birth date: $birthDate")
        println("Gender: $genderValue")
        println("Number: ${getNumber()}")
        println("Time created: $createdTime")
        println("Time last edit: $editedTime")
    }

    override fun getFields(): List<String> = listOf("name", "surname", "birth", "gender", "number")

    override fun getField(field: String): String = when (field) {
        "name" -> name
        "surname" -> surname
        "birth" -> birthDate
        "gender" -> genderValue
        "number" -> getNumber()
        else -> ""
    }

    override fun setField(field: String, value: String) {
        when (field) {
            "name" -> name = value
            "surname" -> surname = value
            "birth" -> {
                if (value.isBlank()) {
                    println("Bad birth date!")
                    birthDate = "[no data]"
                } else {
                    birthDate = value
                }
            }
            "gender" -> {
                if (value != "M" && value != "F") {
                    println("Bad gender!")
                    genderValue = "[no data]"
                } else {
                    genderValue = value
                }
            }
            "number" -> setNumber(value)
        }
        editedTime = LocalDateTime.now()
    }

    override fun getAllValues(): String = "$name $surname $birthDate $genderValue $phoneNumber"
}

class Organization(
    var organizationName: String,
    var address: String,
    number: String
) : Record() {

    init {
        setNumber(number)
    }

    override fun shortInfo(): String = organizationName

    override fun fullInfo() {
        println("Organization name: $organizationName")
        println("Address: $address")
        println("Number: ${getNumber()}")
        println("Time created: $createdTime")
        println("Time last edit: $editedTime")
    }

    override fun getFields(): List<String> = listOf("name", "address", "number")

    override fun getField(field: String): String = when (field) {
        "name" -> organizationName
        "address" -> address
        "number" -> getNumber()
        else -> ""
    }

    override fun setField(field: String, value: String) {
        when (field) {
            "name" -> organizationName = value
            "address" -> address = value
            "number" -> setNumber(value)
        }
        editedTime = LocalDateTime.now()
    }

    override fun getAllValues(): String = "$organizationName $address $phoneNumber"
}

fun saveRecords(records: List<Record>, fileName: String) {
    try {
        ObjectOutputStream(FileOutputStream(fileName)).use { it.writeObject(records) }
    } catch (e: Exception) {
    }
}

@Suppress("UNCHECKED_CAST")
fun loadRecords(fileName: String): MutableList<Record> {
    val file = File(fileName)
    if (!file.exists()) return mutableListOf()
    return try {
        ObjectInputStream(FileInputStream(file)).use { it.readObject() as MutableList<Record> }
    } catch (e: Exception) {
        mutableListOf()
    }
}

fun printList(records: List<Record>) {
    records.forEachIndexed { index, record ->
        println("${index + 1}. ${record.shortInfo()}")
    }
}

fun addRecord(records: MutableList<Record>, fileName: String?) {
    print("Enter the type (person, organization): ")
    val type = readln()
    when (type) {
        "person" -> {
            print("Enter the name: ")
            val name = readln()
            print("Enter the surname: ")
            val surname = readln()
            print("Enter the birth date: ")
            val birth = readln()
            print("Enter the gender (M, F): ")
            val gender = readln()
            print("Enter the number: ")
            val number = readln()
            records.add(Person(name, surname, birth, gender, number))
        }
        "organization" -> {
            print("Enter the organization name: ")
            val orgName = readln()
            print("Enter the address: ")
            val address = readln()
            print("Enter the number: ")
            val number = readln()
            records.add(Organization(orgName, address, number))
        }
    }
    if (fileName != null) saveRecords(records, fileName)
    println("The record added.\n")
}

fun editRecord(record: Record) {
    val fields = record.getFields()
    print("Select a field (${fields.joinToString(", ")}): ")
    val field = readln()
    if (field in fields) {
        print("Enter $field: ")
        val value = readln()
        record.setField(field, value)
        println("Saved")
    }
}

fun searchAction(records: MutableList<Record>, fileName: String?) {
    var currentResults: List<Record> = emptyList()

    fun doSearch() {
        print("Enter search query: ")
        val query = readln()
        val regex = query.toRegex(RegexOption.IGNORE_CASE)
        currentResults = records.filter { it.getAllValues().contains(regex) }
        println("Found ${currentResults.size} results:")
        printList(currentResults)
        println()
    }

    doSearch()

    while (true) {
        print("[search] Enter action ([number], back, again): ")
        val action = readln()
        when {
            action == "back" -> {
                println()
                return
            }
            action == "again" -> {
                doSearch()
            }
            action.toIntOrNull() != null -> {
                val index = action.toInt() - 1
                if (index in currentResults.indices) {
                    val actualRecord = currentResults[index]
                    recordAction(records, actualRecord, fileName)
                    return
                }
            }
        }
    }
}

fun listAction(records: MutableList<Record>, fileName: String?) {
    printList(records)
    println()
    while (true) {
        print("[list] Enter action ([number], back): ")
        val action = readln()
        if (action == "back") {
            println()
            return
        }
        val index = action.toIntOrNull()?.minus(1)
        if (index != null && index in records.indices) {
            recordAction(records, records[index], fileName)
            return
        }
    }
}

fun recordAction(records: MutableList<Record>, record: Record, fileName: String?) {
    record.fullInfo()
    println()
    while (true) {
        print("[record] Enter action (edit, delete, menu): ")
        val action = readln()
        when (action) {
            "edit" -> {
                editRecord(record)
                if (fileName != null) saveRecords(records, fileName)
                record.fullInfo()
                println()
            }
            "delete" -> {
                records.remove(record)
                if (fileName != null) saveRecords(records, fileName)
                println("Deleted\n")
                return
            }
            "menu" -> {
                println()
                return
            }
        }
    }
}

fun main(args: Array<String>) {
    val fileName = if (args.isNotEmpty()) args[0] else null
    val records = if (fileName != null) loadRecords(fileName) else mutableListOf()

    while (true) {
        print("[menu] Enter action (add, list, search, count, exit): ")
        val action = readln()
        when (action) {
            "add" -> addRecord(records, fileName)
            "list" -> listAction(records, fileName)
            "search" -> searchAction(records, fileName)
            "count" -> {
                println("The Phone Book has ${records.size} records.")
                println()
            }
            "exit" -> return
        }
    }
}

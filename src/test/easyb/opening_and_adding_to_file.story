import com.ihasama.ohtu.*
import com.ihasama.ohtu.data_access.*
import com.ihasama.ohtu.domain.*
import com.ihasama.ohtu.io.*
import com.ihasama.ohtu.ui.*
import com.ihasama.ohtu.util.*

description 'User can open and add to a BibTex file'

scenario "user can open a file", {
    given 'a file exists', {
        memoryRefDao = new ReferenceMemoryDao()
        io = new StubIO("2", "article", "id", "title", "A New Article", "", "3")
        app = new App(io, memoryRefDao)
        app.runConsole()
        File file = new File("test.bib")
        file.delete()
        file = new File("test.bib")
        FileUtils.writeDaoToFile(new FileIO(file), memoryRefDao)
        memoryRefDao.removeAll();
    }

    when 'command open file is selected', {
        io2 = new StubIO("1", "3")
        app = new App(io2, memoryRefDao)
        app.runConsole()
    }

    then 'file is open', {
        io.getPrints().shouldHave("@article{id,\ntitle = {A New Article},\n")
        File file = new File("test.bib")
        file.delete()
    }
}

scenario "user can modify opened file", {
    given 'file is open', {
        memoryRefDao = new ReferenceMemoryDao()
        io = new StubIO("2", "article", "id", "title", "A New Article", "", "3")
        app = new App(io, memoryRefDao)
        app.runConsole()
        File file = new File("test.bib")
        file.delete()
        file = new File("test.bib")
        FileUtils.writeDaoToFile(new FileIO(file), memoryRefDao)
        memoryRefDao = FileUtils.readDaoFromFile(file)
    }

    when 'file is modified and saved', {
        memoryRefDao = new ReferenceMemoryDao()
        io = new StubIO("2", "article", "id2", "title", "A New Article2", "", "3")
        app = new App(io, memoryRefDao)
        app.runConsole()
        File file = new File("test.bib")
        FileUtils.writeDaoToFile(new FileIO(file), memoryRefDao)
    }

    then 'file has changed accordingly to modification', {
        Scanner scanner = new Scanner(new File("test.bib"))
        reference = new StringBuilder()
        while (scanner.hasNextLine()) {
            reference.append(scanner.nextLine())
        }
        reference.toString().shouldBe("@article{id,title = {A New Article},}@article{id2,title = {A New Article2},}")
        File file = new File("test.bib")
        file.delete()
    }
}
import com.ihasama.ohtu.*
import com.ihasama.ohtu.data_access.*
import com.ihasama.ohtu.domain.*
import com.ihasama.ohtu.io.*
import com.ihasama.ohtu.ui.*
import com.ihasama.ohtu.util.*

description 'User can save a new BibTex file'

scenario "user can save a new file", {
    given 'command save file has been selected', {
        memoryRefDao = new ReferenceMemoryDao()
        io = new StubIO("2", "article", "id", "title", "A New Article", "", "3")
        app = new App(io, memoryRefDao)
        
    }

    when 'a valid reference has been added', {
        app.runConsole()
        File file = new File("test1.bib")
        FileUtils.writeDaoToFile(file, memoryRefDao)
    }

    then 'a new file is generated', {
        Scanner scanner = new Scanner(new File("test1.bib"))
        reference = new StringBuilder()
        while (scanner.hasNextLine()) {
            reference.append(scanner.nextLine())
        }
        reference.toString().shouldBe("@article{id,title = {A New Article},}")
    }
}
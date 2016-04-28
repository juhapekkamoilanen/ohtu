import com.ihasama.ohtu.*
import com.ihasama.ohtu.data_access.*
import com.ihasama.ohtu.domain.*
import com.ihasama.ohtu.io.*
import com.ihasama.ohtu.ui.*
import com.ihasama.ohtu.util.*


description 'User can delete a reference'

scenario "user can delete the only existing reference", {
    given 'reference exists', {
        memoryRefDao = new ReferenceMemoryDao()
        io = new StubIO("2", "article", "id1", "title", "A New Article", "", "3")
        app = new App(io, memoryRefDao)
        app.runConsole()
    }

    when 'a reference is chosen to be deleted', {
        io = new StubIO("4", "id1", "1", "3")
        app = new App(io, memoryRefDao)
        app.runConsole()
    }

    then 'the chosen reference will be deleted', {
        memoryRefDao.getObjects().size().shouldBe(0);
        io.getPrints().shouldHave("No references.\n")
    }
}

scenario "user can delete a reference from existing references", {
    given 'reference exists', {
        memoryRefDao = new ReferenceMemoryDao()
        io = new StubIO("2", "article", "id1", "title", "A New Article", "", "2", "article", "id2", "title", "A New Article2", "", "3")
        app = new App(io, memoryRefDao)
        app.runConsole()
    }

    when 'a reference is chosen to be deleted', {
        io = new StubIO("4", "id1", "1", "3")
        app = new App(io, memoryRefDao)
        app.runConsole()
    }

    then 'the chosen reference will be deleted', {
        memoryRefDao.getObjects().size().shouldBe(1);
        io.getPrints().shouldHave("@article{id2,\ntitle = {A New Article2},\n")
    }
}
import com.ihasama.ohtu.*
import com.ihasama.ohtu.data_access.*
import com.ihasama.ohtu.domain.*
import com.ihasama.ohtu.io.*
import com.ihasama.ohtu.ui.*
import com.ihasama.ohtu.util.*


description 'User filter references with a keyword'

scenario "user can see references that include chosen keyword", {
    given 'references exist', {
        memoryRefDao = new ReferenceMemoryDao()
        io = new StubIO("2", "article", "id1", "title", "A New Article", "", "2", "article", "id2", "title", "A New Article2", "", "3")
        app = new App(io, memoryRefDao)
        app.runConsole()
    }

    when 'a filter keyword is given', {
        io = new StubIO("5", "1", "3")
        app = new App(io, memoryRefDao)
        app.runConsole()
    }

    then 'references based on the keyword are shown', {
        io.getPrints().shouldHave("@article{id1,\ntitle = {A New Article},\n")
        io.getPrints().shouldNotHave("@article{id2,\ntitle = {A New Article2},\n")
    }
}

scenario "user gives a filtering keyword that does not match any reference", {
    given 'references exist', {
        memoryRefDao = new ReferenceMemoryDao()
        io = new StubIO("2", "article", "id1", "title", "A New Article", "", "2", "article", "id2", "title", "A New Article2", "", "3")
        app = new App(io, memoryRefDao)
        app.runConsole()
    }

    when 'a filter keyword is given that gives no matches', {
        io = new StubIO("5", "thisdoesnotexist", "3")
        app = new App(io, memoryRefDao)
        app.runConsole()
    }

    then 'user is notified about empty filtered list', {
        io.getPrints().shouldHave("References containing keyword do not exist\n")
        io.getPrints().shouldNotHave("@article{id1,\ntitle = {A New Article},\n")
        io.getPrints().shouldNotHave("@article{id2,\ntitle = {A New Article2},\n")
    }
}
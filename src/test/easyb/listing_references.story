import com.ihasama.ohtu.*
import com.ihasama.ohtu.data_access.*
import com.ihasama.ohtu.domain.*
import com.ihasama.ohtu.io.*

description 'User can see a list of existing references'

scenario "User can ask for references when none have been added", {
    given 'command add list references is selected', {
        memoryRefDao = new MemoryReferenceDao()
        io = new StubIO("1", "3")
        app = new App(io, memoryRefDao)
    }
    
    when 'no references exist', {
        app.run()
    }

    then 'user is notified about the empty reference list', {
        io.getPrints().shouldHave("No references.\n")
    }
} 

scenario "User can get a list of existing references", {
    given 'command add list references is selected', {
        memoryRefDao = new MemoryReferenceDao()
        memoryRefDao.add(new Reference(EntryType.ARTICLE, "id"))
        io = new StubIO("1", "3")
        app = new App(io, memoryRefDao)
       
    }

    when 'references exist', {
        app.run()
    }

    then 'existing references printed', {
        io.getPrints().shouldHave("@article{id,\n}")
    }
}

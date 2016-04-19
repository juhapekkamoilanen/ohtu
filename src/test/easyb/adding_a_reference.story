import com.ihasama.ohtu.*
import com.ihasama.ohtu.data_access.*
import com.ihasama.ohtu.domain.*
import com.ihasama.ohtu.io.*

description 'User can add a new reference'

scenario "user can add a reference of a valid type with a valid field", {
    given 'command add new references selected', {
        memoryRefDao = new ReferenceMemoryDao()
        io = new StubIO("2", "article", "id", "title", "A New Article", "", "3")
        app = new App(io, memoryRefDao)
       
    }

    when 'a valid type and fields entered', {
        app.runConsole()
    }

    then 'a new reference will be added', {
        io.getPrints().shouldHave("Reference added successfully.")
    }
}

scenario "user can not add a reference of invalid type", {
    given 'command add new references selected', {
        memoryRefDao = new ReferenceMemoryDao()
        io = new StubIO("2", "invalid", "article", "id", "title", "A New Article", "", "3")
        app = new App(io, memoryRefDao)
    }
    
    when 'an invalid type entered', {
        app.runConsole()
    }

    then 'notify about invalid type', {
        io.getPrints().shouldHave("Illegal reference type.")
    }
}

scenario "user can not add an invalid field", {
    given 'command add new references selected', {
        memoryRefDao = new ReferenceMemoryDao()
        io = new StubIO("2", "article", "id", "invalid", "title", "A New Article", "", "3")
        app = new App(io, memoryRefDao)
    }
    
    when 'an invalid field entered', {
        app.runConsole()
    }

    then 'notify about invalid field', {
        io.getPrints().shouldHave("Illegal field type.")
    }
}


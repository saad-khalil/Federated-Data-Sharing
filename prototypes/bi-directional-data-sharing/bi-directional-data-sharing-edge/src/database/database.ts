import { defineStore, } from "pinia";
import { useSessionStorage, type RemovableRef } from "@vueuse/core"
import type { Database, Table, Row, Entry } from "~/types/Databse/Database";
import { v4 as uuidv4 } from 'uuid';


export const useDatabase = defineStore("databse", () => {
    const database : RemovableRef<Database> = useSessionStorage<Database>("databse", {
        tables: [{
            name: "Items",
            rows: []
        },
        {
            name: "Trucks",
            rows: []
        },
        {
            name: "Routes",
            rows: []
        },
        {
            name: "Warehouses",
            rows: []
        }]
    })

    const client : ComputedRef<Database> = computed(() => database.value)

    function table(name: string) : Table<Record<string, unknown>> | undefined {
        return client.value.tables.find( (table: Table<Record<string, unknown>>) => table.name === name )
    }

    function create<
        T extends Record<string, unknown>
    >(name: string):
        Table <T> | null {
        const newTable = <Table<T>>{
            name: name,
            rows: <Row<T>[]>[]
        }

        const table = database.value.tables.find((table: Table<Record<string, unknown>>) => table.name = name)

        if(table){
            return null;
        }

        database.value.tables.push(newTable)
        return newTable
    }

    function findById<
        T extends Table<Record<string, unknown>>
    >(table: T, id: string):
        Row<Record<string, unknown>> | undefined {
        return table.rows.find( (row: Row<Record<string,unknown>>) => row.entry.subject.id = id)
    }

    function add<
        T extends Table<Record<string, unknown>>,
        U extends Record<string,unknown>
    >(table: T, object: U):
        Row<Record<string,unknown>> {
        const newRow: Row<U> = {
            id: uuidv4(),
            count: table.rows.length + 1,
            entry: <Entry<U>>{
                id: uuidv4(),
                subject: object
            }
        }
        table.rows.push(newRow)
        return newRow
    }

    function remove<
        T extends Table<Record<string, unknown>>,
        U extends Record<string,unknown>
    >(table: T, object: U):
        void {
        const item = table.rows.find((row: Row<Record<string, unknown>>) => row.entry.subject === object);

        if(!item){
            return
        }
        const index = table.rows.indexOf(item)
        table.rows.splice(index, 1)
    }


    return {
        client, create, table, findById, add, remove,database
    }

},{
    persist: true
})
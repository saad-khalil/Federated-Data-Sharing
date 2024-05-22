export type Database = {
    tables: Table[],

}

export type Table <U = Record<string, unknown>> = {
    name: string,
    rows: Row<U>[]
}

export type Row<T> = {
    id: string,
    count: number,
    entry: Entry<T>
}

export type Entry<T> = {
    id: string
    subject: T
}

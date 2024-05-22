export type ConnectionAddress = {
    address: string;
    port: number;
}

export type ServerInstance = {
    origin: ServerOrigin
    institution: Institution
}

export type ServerOrigin = {
    name: string
    id: string,
}

export type Institution = {
    name: string,
    id: string,
}

export type Connection = {
    address: ConnectionAddress,
    server: ServerInstance
}
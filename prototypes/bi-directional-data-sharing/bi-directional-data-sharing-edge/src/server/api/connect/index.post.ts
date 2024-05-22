import { useStorage } from "#imports"
import { defineEventHandler, readBody } from "h3"

export default defineEventHandler(async (event) => {
    const request_body : {
        connection: {
            source: {
                address: string,
                port: number
            },
            target: {
                address: string,
                port: number
            },
            connections: {
                address: {
                    address: string,
                    port: string
                },
                server: {
                    origin: {
                        name: string,
                        id: string
                    },
                    institution: {
                        name: string,
                        id: string
                    }
                }
            }[  ]

        }
    } = await readBody<{
        connection: {
            source: {
                address: string,
                port: number
            },
            target: {
                address: string,
                port: number
            },
            connections: {
                address: {
                    address: string,
                    port: string
                },
                server: {
                    origin: {
                        name: string,
                        id: string
                    },
                    institution: {
                        name: string,
                        id: string
                    }
                }
            }[]

        }
    }>(event)

    const servers : {
        address: {
            address: string,
            port: string
        },
        server: {
            origin: {
                name: string,
                id: string
            },
            institution: {
                name: string,
                id: string
            }
        }
    }[] = await useStorage().getItem('servers')
    const merge = (a, b, predicate = (a, b) => a === b) => {
        const c = [...a]; // copy to avoid side effects
        // add all items from B to copy C if they're not already present
        b.forEach((bItem) => (c.some((cItem) => predicate(bItem, cItem)) ? null : c.push(bItem)))
        return c;
    }

    const newConn = merge(servers, request_body.connection.connections)

    await useStorage().setItem('servers', newConn)

    return await useStorage().getItem('servers')

});
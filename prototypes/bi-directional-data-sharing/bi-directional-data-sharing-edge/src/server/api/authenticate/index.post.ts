import { useStorage } from "#imports"
import { defineEventHandler, readBody } from "h3"


export default defineEventHandler(async (event) => {
    const result  = await readBody(event)

    await useStorage().setItem('servers', [result]);

    return await useStorage().getItem('servers')

});
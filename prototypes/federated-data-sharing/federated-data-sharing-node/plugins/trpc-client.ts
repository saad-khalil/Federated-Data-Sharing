import { createTRPCNuxtClient, httpBatchLink } from 'trpc-nuxt/client'
import type { AppRouter } from '~/server/trpc/routers'

export default defineNuxtPlugin(() => {

    const trpcClient = createTRPCNuxtClient<AppRouter>({
        links: [
            httpBatchLink({
                url: 'http://localhost:3000/api/trpc'
            })
        ]
    })


    return {
        provide: {
            trpcClient
        }
    }
})
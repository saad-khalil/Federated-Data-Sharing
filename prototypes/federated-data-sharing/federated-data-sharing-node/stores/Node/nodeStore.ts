import { defineStore } from "pinia"
import { useToast } from "vue-toastification";

export const useFederatedNodeStore = defineStore("nodes", () => {

    const { $trpcClient } = useNuxtApp();
    const { federatedNodes } = $trpcClient
    const toast = useToast();

    async function create_node(node_information: {
        name: string,
        security: "private" | "subscribe" | "public"

    }) {
        const { data } = await federatedNodes.addNode.useQuery({
            name: node_information.name,
            security: node_information.security
        })

        return data.value?.response.payload

    }

    return {
        create_node
    }
}, {
    persist: true
})